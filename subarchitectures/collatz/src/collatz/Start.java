package collatz;

import cast.CASTException;
import cast.architecture.ChangeFilterFactory;
import cast.architecture.ManagedComponent;
import cast.architecture.WorkingMemoryChangeReceiver;
import cast.cdl.CASTTime;
import cast.cdl.WorkingMemoryChange;
import cast.cdl.WorkingMemoryOperation;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jeremiah Via <jxv911@cs.bham.ac.uk>
 */
public class Start extends ManagedComponent implements WorkingMemoryChangeReceiver {

    private int max;
    private Random random;

    public Start()
    {
        random = new Random();
    }

    @Override
    protected void configure(Map<String, String> _config)
    {
        if (_config.containsKey("--max")) {
            max = Integer.parseInt(_config.get("--max"));
        } else {
            max = 100;
        }
    }

    @Override
    protected void start()
    {
        addChangeFilter(ChangeFilterFactory.createLocalTypeFilter(Number.class, WorkingMemoryOperation.DELETE), this);

        try {
            workingMemoryChanged(null);
        } catch (CASTException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void workingMemoryChanged(WorkingMemoryChange wmc) throws CASTException
    {
        int val = random.nextInt(max);
        println("---------------------------");
        println(String.format("Value: %4d      Count: %3d", val, 0));
        addToWorkingMemory(newDataID(), new Number(val, 0));
    }
}
