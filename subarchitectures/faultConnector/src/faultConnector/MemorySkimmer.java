package faultConnector;

import cast.CASTException;
import cast.architecture.ChangeFilterFactory;
import cast.architecture.ManagedComponent;
import cast.architecture.WorkingMemoryChangeReceiver;
import cast.cdl.CASTTime;
import cast.cdl.WorkingMemoryChange;
import cast.cdl.WorkingMemoryOperation;
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
 * This is a simple component which collects data and makes it available to
 * the fault detecting software. 
 *
 * @author Jeremiah Via <jxv911@cs.bham.ac.uk>
 */
public class MemorySkimmer extends ManagedComponent implements WorkingMemoryChangeReceiver {

    private ServerSocket server;
    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    public static int PORT = 5555;

    /**
     * Create object & open port.
     */
    public MemorySkimmer()
    {
        try {
            server = new ServerSocket(PORT);
            client = new Socket();
        } catch (IOException ex) {
            Logger.getLogger(MemorySkimmer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Start up the component by registering it for all events and opening a 
     * connection to the fault detector.
     */
    @Override
    protected void start()
    {

        // Create a non-blocking server connection
        new Timer().scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run()
            {
                /*if (client.isConnected()) {
                cancel();
                return;
                }*/
                try {
                    server.setSoTimeout(100);
                    client = server.accept();

                    if (client.isConnected()) {
                        out = new ObjectOutputStream(client.getOutputStream());
                        out.flush();
                        in = new ObjectInputStream(client.getInputStream());
                        cancel();
                        println("Connected!");
                    }
                } catch (IOException ex) {
                    log("Waiting for connection");
                }
            }
        }, 0, 100);

        // Subscribe to all change events
        for (String name : getComponentManager().getComponentDescriptions().keySet())
            addChangeFilter(ChangeFilterFactory.createSourceFilter(name, WorkingMemoryOperation.WILDCARD), this);

        println("Now skimming...");
    }

    /**
     * Call back method that is executed when any inter-component communication 
     * occurs.
     * 
     * The method simply collects the data necessary for the fault detector 
     * and writes it to a socket the fault detector is subscribing to.
     * 
     * @param wmc the change in working memory
     * @throws CASTException bad memory change
     */
    @Override
    public void workingMemoryChanged(WorkingMemoryChange wmc) throws CASTException
    {
        // Nothing to write until we have a connection
        if (!client.isConnected())
            return;

        try {
            out.writeObject(new String[]{String.valueOf(Cast2Ms(wmc.timestamp)), wmc.operation.name(), wmc.src, wmc.address.id});
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(MemorySkimmer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Convert cast time to milliseconds.
     * 
     * @param ct cast time object containing seconds and microseconds.
     * @return milliseconds
     */
    private long Cast2Ms(CASTTime ct)
    {
        return 1000 * ct.s + (ct.us / 1000);
    }

    /**
     * Allow for graceful destruction of the component. 
     * 
     * This allows the component to send out a message to the client so it can
     * close its own connection.
     */
    @Override
    public void destroy()
    {
        if (!client.isConnected())
            return;

        try {
            out.writeObject(new String[]{"done"});
            out.flush();
            out.close();
            client.close();
            server.close();
        } catch (IOException ex) {
            Logger.getLogger(MemorySkimmer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void monitorExit()
    {

        new Thread(new Runnable() {

            @Override
            public void run()
            {
                try {
                    if (in.read() == -1) {
//                    String s = in.readUTF();
//                    if (s.equals("done")){
                        
                        System.out.println("-1");
                        out.close();
                        in.close();
                        client.close();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(MemorySkimmer.class.getName()).log(Level.SEVERE, null, ex);
                }
                start();
            }
        }).start();
    }
}
