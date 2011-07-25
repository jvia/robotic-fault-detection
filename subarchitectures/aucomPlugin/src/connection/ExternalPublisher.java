package connection;

import cast.architecture.ManagedComponent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
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
            server = new ServerSocket(port);
            client = server.accept();
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
    }

    @Override
    protected void destroy()
    {
        // shutdown the client socket if it is still connected
        if (client.isConnected()) {
            try {
                output.writeChars(".");
            } catch (IOException ex) {
                log(Level.SEVERE, "Could not send shutdown message", ex);
            }
        }
        
        try {
            server.close();
        } catch (IOException ex) {
            log(Level.INFO, "IO waiting...", ex);
        }
    }

    /**
     * To make the logging less obtrusive.
     * 
     * @param lvl logging level
     * @param msg description
     * @param ex  error class
     */
    private void log(Level lvl, String msg, Object ex)
    {
        Logger.getLogger(ExternalPublisher.class.getName()).log(lvl, msg, ex);
    }
}
