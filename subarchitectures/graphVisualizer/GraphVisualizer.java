package graphVisualizer;

import cast.CASTException;
import cast.architecture.ChangeFilterFactory;
import cast.architecture.ManagedComponent;
import cast.architecture.WorkingMemoryChangeReceiver;
import cast.cdl.CASTTime;
import cast.cdl.WorkingMemoryChange;
import cast.cdl.WorkingMemoryOperation;

/**
 *
 * @author Jeremiah Via <jxv911@cs.bham.ac.uk>
 */
public class GraphVisualizer extends ManagedComponent implements WorkingMemoryChangeReceiver {

    @Override
    public void start()
    {
        addChangeFilter(ChangeFilterFactory.createOperationFilter(WorkingMemoryOperation.WILDCARD), this);
        println("Initializing XML");
    }

    @Override
    public void workingMemoryChanged(WorkingMemoryChange wmc) throws CASTException
    {
        String xml = String.format( "\n       <timestamp>%d</timestamp>"
                                  + "\n       <src>%s</src>"
                                  + "\n       <op>%s</op>"
                                  + "\n       <id>%s</id>\n", 
                                  CastTimeToMilliseconds(wmc.timestamp),
                                  wmc.src, wmc.operation, wmc.address.id);
        println(xml);
    }

    /**
     * 
     * @param ct
     * @return 
     */
    private long CastTimeToMilliseconds(CASTTime ct)
    {
        return 1000 * ct.s + (ct.us / 1000);
    }
}
