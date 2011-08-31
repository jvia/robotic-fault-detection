package count;

import cast.CASTException;
import cast.architecture.ChangeFilterFactory;
import cast.architecture.ManagedComponent;
import cast.architecture.WorkingMemoryChangeReceiver;
import cast.cdl.CASTTime;
import cast.cdl.WorkingMemoryChange;
import cast.cdl.WorkingMemoryOperation;

/**
 * Just count memory events. Used to control the induction of errors.
 * 
 * @author jxv911
 */
public class Count extends ManagedComponent implements WorkingMemoryChangeReceiver {

    private static int count;
    public static final int ERROR_COUNT = Integer.MAX_VALUE; // so no error
    public static final int SHUTDOWN_COUNT = 2050;

    public Count()
    {
        count = 0;
    }

    @Override
    public void start()
    {
        StringBuilder b = new StringBuilder("Listening on: ");
        for (String name : getComponentManager().getComponentDescriptions().keySet()) {
            b.append(name).append(" ");
            addChangeFilter(ChangeFilterFactory.createSourceFilter(name, WorkingMemoryOperation.WILDCARD), this);
        }
        println(b);
//        addChangeFilter(ChangeFilterFactory.createOperationFilter(WorkingMemoryOperation.ADD), this);
//        addChangeFilter(ChangeFilterFactory.createOperationFilter(WorkingMemoryOperation.DELETE), this);
//        addChangeFilter(ChangeFilterFactory.createOperationFilter(WorkingMemoryOperation.OVERWRITE), this);
    }

    @Override
    public void workingMemoryChanged(WorkingMemoryChange wmc) throws CASTException
    {
        ++count;

        if (count % 10 == 0) {
            println(">>>>> " + count);
        }

        // We're collecting 4,000 observations in the time series
        if (count == SHUTDOWN_COUNT) {
            println(">>>>>>>>>> System Going Down <<<<<<<<<<");
            System.exit(0);
        }

    }

    public static int getCount()
    {
        return count;
    }

    public static void reset()
    {
        count = 0;
    }

    public static long cast2Ms(CASTTime ct)
    {
        return 1000 * ct.s + (ct.us / 1000);
    }
}
