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
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * TODO: import nu.xom.Element
 *
 * @author Jeremiah Via <jxv911@cs.bham.ac.uk>
 */
public class MemorySkimmer extends ManagedComponent implements WorkingMemoryChangeReceiver {

    private ServerSocket server;
    private Socket client;
    private ObjectOutputStream /*PrintWriter*/ out;
    public static int PORT = 5555;

    public MemorySkimmer()
    {
        try {
            server = new ServerSocket(PORT);
        } catch (IOException ex) {
            Logger.getLogger(MemorySkimmer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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

        println("Skimmer now skimming...");
    }

    @Override
    public void workingMemoryChanged(WorkingMemoryChange wmc) throws CASTException
    {
        String sdata = String.format("%d %s %s %s\n", Cast2Ms(wmc.timestamp), wmc.operation, wmc.src, wmc.address.id);
        println(sdata);
        String[] data = {String.valueOf(Cast2Ms(wmc.timestamp)), wmc.operation.name(), wmc.src, wmc.address.id};

        try {
            out.writeObject(data);
            out.flush();

            //                String.format("\n"
            //                + "<timestamp>%d</timestamp>\n"
            //                + "<operation>%s</operation>\n"
            //                + "<src>%s</src>\n"
            //                + "<address>\n"
            //                + "  <id>%s</id>\n"
            //                + "  <subarchitecture>%s</subarchitecture>\n"
            //                + "</address>\n"
            //                + "<type>%s</type>\n",
            //                              Cast2Ms(wmc.timestamp), wmc.operation, wmc.src, wmc.address.id, wmc.address.subarchitecture, wmc.type)
        } catch (IOException ex) {
            Logger.getLogger(MemorySkimmer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private long Cast2Ms(CASTTime ct)
    {
        return 1000 * ct.s + (ct.us / 1000);
    }

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
