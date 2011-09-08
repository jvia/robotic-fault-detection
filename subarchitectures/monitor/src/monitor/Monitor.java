package monitor;

import cast.AlreadyExistsOnWMException;
import cast.CASTException;
import cast.architecture.ChangeFilterFactory;
import cast.architecture.ManagedComponent;
import cast.architecture.WorkingMemoryChangeReceiver;
import cast.cdl.WorkingMemoryChange;
import cast.cdl.WorkingMemoryOperation;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

import count.Count;
import updater.UpdateMessage;

/**
 *
 * @author Jeremiah Via <jxv911@cs.bham.ac.uk>
 */
public class Monitor extends ManagedComponent implements WorkingMemoryChangeReceiver {

    private int max;
    private int min;
    private int mean;
    private int updatesReceived;
    private String address;

    public Monitor()
    {
        max = Integer.MIN_VALUE;
        min = Integer.MAX_VALUE;
        mean = 0;
        updatesReceived = 0;
        address = newDataID();
    }

    @Override
    protected void start()
    {
        try {
            addToWorkingMemory(address, new MonitorMessage(max, min, mean));
        } catch (AlreadyExistsOnWMException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        addChangeFilter(ChangeFilterFactory.createTypeFilter(UpdateMessage.class, WorkingMemoryOperation.ADD), this);
        println("Monitor initialized");
    }

    @Override
    public void workingMemoryChanged(WorkingMemoryChange wmc) throws CASTException
    {
        int update = getMemoryEntry(wmc.address, UpdateMessage.class).msg;

        // Update the min and max values
        max = Math.max(max, update);
        min = Math.min(min, update);

        // Update the mean
        mean = (update + (updatesReceived * mean)) / (updatesReceived + 1);

        // current update becomes the lastUpdate and increment updatesReceived
        updatesReceived++;

        // Overwrite monitor message
//        if (Count.isErrorCondition())
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        overwriteWorkingMemory(address, new MonitorMessage(max, min, mean));
        println(String.format("Max: %5d  Min: %5d  Mean: %5d", max, min, mean));
    }
}
