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
    // Error behavior
    //public static final int ERROR_COUNT = 2000;
    //public static final int RECOVERY_COUNT = 3000;
    //public static final int SHUTDOWN_COUNT = 4200;
    // No error
    public static final int ERROR_COUNT = Integer.MAX_VALUE;
    public static final int RECOVERY_COUNT = Integer.MAX_VALUE;
    public static final int SHUTDOWN_COUNT = Integer.MAX_VALUE;

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
    }

    @Override
    public void workingMemoryChanged(WorkingMemoryChange wmc) throws CASTException
    {
        ++count;

        if (count % 100 == 0) {
            println(">>>>> " + count);
        }
        
        if (count == ERROR_COUNT)
            println(">>>>>>>>>> ERROR INDUCED AT " + cast2Ms(wmc.timestamp) + " ms <<<<<<<<<<");

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

    public static boolean isErrorCondition()
    {
        return ERROR_COUNT < count && count < RECOVERY_COUNT;
    }
}
