package faultConnector;

import cast.CASTException;
import cast.architecture.ChangeFilterFactory;
import cast.architecture.ManagedComponent;
import cast.architecture.WorkingMemoryChangeReceiver;
import cast.cdl.CASTTime;
import cast.cdl.ComponentDescription;
import cast.cdl.WorkingMemoryChange;
import cast.cdl.WorkingMemoryOperation;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
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
    public static int PORT = 5555;

    /**
     * Create object & open port.
     */
    public MemorySkimmer()
    {
        try {
            server = new ServerSocket(PORT);
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
        try {
            println("Waiting for connection from fault detector...");
            client = server.accept();
            out = new ObjectOutputStream(client.getOutputStream())/*PrintWriter(client.getOutputStream(), true)*/;
        } catch (IOException ex) {
            Logger.getLogger(MemorySkimmer.class.getName()).log(Level.SEVERE, null, ex);
        }


        Map<String, ComponentDescription> comps = getComponentManager().getComponentDescriptions();
        for (String name : comps.keySet()) {
            addChangeFilter(ChangeFilterFactory.createSourceFilter(name, WorkingMemoryOperation.WILDCARD), this);
        }

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
}
