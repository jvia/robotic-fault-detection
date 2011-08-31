package collatz;

import cast.CASTException;
import cast.DoesNotExistOnWMException;
import cast.architecture.ChangeFilterFactory;
import cast.architecture.ManagedComponent;
import cast.architecture.WorkingMemoryChangeReceiver;
import cast.cdl.WorkingMemoryChange;
import cast.cdl.WorkingMemoryOperation;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jeremiah Via <jxv911@cs.bham.ac.uk>
 */
public class Even extends ManagedComponent implements WorkingMemoryChangeReceiver {

    @Override
    protected void start()
    {
        addChangeFilter(ChangeFilterFactory.createLocalTypeFilter(Number.class, WorkingMemoryOperation.ADD), this);
        addChangeFilter(ChangeFilterFactory.createLocalTypeFilter(Number.class, WorkingMemoryOperation.OVERWRITE), this);
    }

    @Override
    public void workingMemoryChanged(WorkingMemoryChange wmc) throws CASTException
    {
        Number num;
        try {
            num = getMemoryEntry(wmc.address, Number.class);
        } catch (DoesNotExistOnWMException ex) {
            return;
        }

        if (num.value > 1 && num.value % 2 == 0) {
            println(String.format(" Value: %4d      Count: %3d", num.value, num.count));

            try {
                Thread.sleep(num.value);
            } catch (InterruptedException ex) {
                Logger.getLogger(End.class.getName()).log(Level.SEVERE, "Could not sleep", ex);
            }

            num.value /= 2;
            num.count++;
            overwriteWorkingMemory(wmc.address, num);
        }
    }
}
