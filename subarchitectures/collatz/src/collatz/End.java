package collatz;

import cast.CASTException;
import cast.architecture.ChangeFilterFactory;
import cast.architecture.ManagedComponent;
import cast.architecture.WorkingMemoryChangeReceiver;
import cast.cdl.WorkingMemoryChange;
import cast.cdl.WorkingMemoryOperation;

/**
 *
 * @author Jeremiah Via <jxv911@cs.bham.ac.uk>
 */
public class End extends ManagedComponent implements WorkingMemoryChangeReceiver {

    @Override
    protected void start()
    {
        addChangeFilter(ChangeFilterFactory.createLocalTypeFilter(Number.class, WorkingMemoryOperation.ADD), this);
        addChangeFilter(ChangeFilterFactory.createLocalTypeFilter(Number.class, WorkingMemoryOperation.OVERWRITE), this);
    }

    @Override
    public void workingMemoryChanged(WorkingMemoryChange wmc) throws CASTException
    {
        Number num = getMemoryEntry(wmc.address, Number.class);

        if (num.value <= 1) {
            println(String.format("  Value: %4d      Count: %3d", num.value, num.count));
            println("-----------------------------");
            System.out.println("");
            deleteFromWorkingMemory(wmc.address);
        }
    }
}
