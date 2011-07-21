package faultConnector;

import cast.CASTException;
import cast.architecture.ChangeFilterFactory;
import cast.architecture.ManagedComponent;
import cast.architecture.WorkingMemoryChangeReceiver;
import cast.cdl.CASTTime;
import cast.cdl.ComponentDescription;
import cast.cdl.WorkingMemoryChange;
import cast.cdl.WorkingMemoryOperation;
import java.util.Map;
import updater.UpdateMessage;

/**
 *
 * @author Jeremiah Via <jxv911@cs.bham.ac.uk>
 */
public class MemorySkimmer extends ManagedComponent implements WorkingMemoryChangeReceiver {

    @Override
    protected void start()
    {
        Map<String, ComponentDescription> comps = getComponentManager().getComponentDescriptions();
        for (String name : comps.keySet()) {
            addChangeFilter(ChangeFilterFactory.createSourceFilter(name, WorkingMemoryOperation.WILDCARD), this);
        }

        addChangeFilter(ChangeFilterFactory.createOperationFilter(WorkingMemoryOperation.WILDCARD), this);
//        addChangeFilter(ChangeFilterFactory., this);
//        addChangeFilter(ChangeFilterFactory.createTypeFilter(UpdateMessage.class, WorkingMemoryOperation.ADD), this);
        println("Skimmer now skimming...");
    }

    @Override
    public void workingMemoryChanged(WorkingMemoryChange wmc) throws CASTException
    {
        println(String.format("\n"
                + "<timestamp>%d</timestamp>\n"
                + "<operation>%s</operation>\n"
                + "<src>%s</src>\n"
                + "<address>\n"
                + "  <id>%s</id>\n"
                + "  <subarchitecture>%s</subarchitecture>\n"
                + "</address>\n"
                + "<type>%s</type>\n", 
                Cast2Ms(wmc.timestamp), wmc.operation, wmc.src, wmc.address.id, wmc.address.subarchitecture, wmc.type));
    }

    private long Cast2Ms(CASTTime ct)
    {
        return 1000 * ct.s + (ct.us / 1000);
    }
}
