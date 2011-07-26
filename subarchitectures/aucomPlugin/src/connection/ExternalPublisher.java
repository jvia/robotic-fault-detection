package connection;

import cast.architecture.ManagedComponent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jeremiah Via <jxv911@cs.bham.ac.uk>
 */
public class ExternalPublisher extends ManagedComponent {

    private int port;
    private ServerSocket server;
    private Socket client;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private boolean sendingMessage;

    public ExternalPublisher(int port)
    {
        this.port = port;
    }

    public ExternalPublisher()
    {
        this(5555);
    }

    @Override
    protected void start()
    {
        // Create server & accept connection from client 
        try {
            println("Waiting...");
            server = new ServerSocket(port);
            client = server.accept();
            println("Connected!");
        } catch (IOException ex) {
            log(Level.SEVERE, "Could not create server", ex);
        }

        // open output stream
        try {
            output = new ObjectOutputStream(client.getOutputStream());
            output.flush();
        } catch (IOException ex) {
            log(Level.SEVERE, "Could not create output stream", ex);
        }

        // open input stream
        try {
            input = new ObjectInputStream(client.getInputStream());
        } catch (IOException ex) {
            log(Level.SEVERE, "Could not create input stream", ex);
        }

        // make a thread to monitor input from client
        new Thread(new Runnable() {

            @Override
            public void run()
            {
                try {
                    String fromCast;
                    boolean done = false;
                    try {
                        while (!done) {
                            fromCast = input.readUTF();
                            log(Level.INFO, String.format("Received %s from client", fromCast), null);

                            // initiate shutdown if cast says bye
                            if (fromCast.equals(".")) {
                                log(Level.INFO, "Received shutdown from client", null);
                                output.writeUTF(".");
                                output.flush();
                                done = true;
                                sendingMessage = false;
                            }
                        }
                    } catch (IOException ex) {
                        log(Level.SEVERE, "Error reading input from client", ex);
                    }

                    client.close();
                } catch (IOException ex) {
                    log(Level.SEVERE, "IO exception closing client connection", ex);
                }
                destroy();
            }
        }).start();

        // start sending data
        sendingMessage = true;
        new Timer().scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run()
            {
                if (sendingMessage) {
                    try {
                        output.writeUTF("hello");
                        output.flush();
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException ex) {
                            log(Level.SEVERE, null, ex);
                        }
                    } catch (IOException ex) {
                        log(Level.SEVERE, "Error writing to client", ex);
                    }
                }
            }
        }, 0, 1000);

    }

    @Override
    protected void destroy()
    {
        println("Shutdown time...");
        sendingMessage = false;


        // shutdown the client socket if it is still connected
        if (!client.isClosed()) {
            try {
                log(Level.INFO, "Sending shutdown signal to external client", null);
                output.writeUTF(".");
                output.flush();
            } catch (IOException ex) {
                log(Level.SEVERE, "Could not send shutdown message", ex);
            }

            try {
                String stop = input.readUTF();
                if (stop.equals(".")) {
                    log(Level.INFO, "Received acknowledgement from client", null);
                    try {
                        server.close();
                    } catch (IOException ex) {
                        log(Level.INFO, "IO waiting...", ex);
                    }
                }
            } catch (IOException ex) {
                log(Level.SEVERE, "Error reading client shutdown response", ex);
            }
        }
    }

    /**
     * To make the logging less intrusive.
     * 
     * @param lvl logging level
     * @param msg description
     * @param ex  error class
     */
    private void log(Level lvl, String msg, Object ex)
    {
        Logger.getLogger(ExternalPublisher.class.getName()).log(lvl, msg, ex);
    }

    private void sendMessages()
    {
        while (sendingMessage) {
            try {
                output.writeUTF("hello");
                output.flush();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                log(Level.SEVERE, "Error writing to client", ex);
            }
        }
        println("We're at the end of sendMessages");
    }
}
