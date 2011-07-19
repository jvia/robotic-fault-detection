package statusUpdater;

import cast.AlreadyExistsOnWMException;
import cast.ConsistencyException;
import cast.DoesNotExistOnWMException;
import cast.PermissionException;
import cast.UnknownSubarchitectureException;
import cast.architecture.ChangeFilterFactory;
import cast.architecture.ManagedComponent;
import cast.architecture.WorkingMemoryChangeReceiver;
import cast.cdl.WorkingMemoryChange;
import cast.cdl.WorkingMemoryOperation;
import java.util.logging.Level;
import java.util.logging.Logger;
import sendReturn.ReturnMessage;

/**
 *
 * @author jxv911
 */
public class Monitor extends ManagedComponent {

    String dataID;

    @Override
    protected void start() {
        super.start();
        println("Initializing Monitor");
        try {
            dataID = newDataID();
            addToWorkingMemory(dataID, new ReturnMessage(Integer.MIN_VALUE));
        } catch (AlreadyExistsOnWMException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        addChangeFilter(ChangeFilterFactory.createLocalTypeFilter(ReturnMessage.class, WorkingMemoryOperation.OVERWRITE),
                new WorkingMemoryChangeReceiver() {

                    @Override
                    public void workingMemoryChanged(WorkingMemoryChange _wmc) {
                        updateMonitor(_wmc);
                    }
                });
    }

    private void updateMonitor(WorkingMemoryChange _wmc) {
        try {

            ReturnMessage msg = getMemoryEntry(_wmc.address, ReturnMessage.class);
            ReturnMessage max = getMemoryEntry(dataID, ReturnMessage.class);

            if (msg.message > max.message) {
                max.message = msg.message;
                println("Max value monitored :: " + max.message);

                try {
                    overwriteWorkingMemory(dataID, max);
                } catch (ConsistencyException ex) {
                    Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (PermissionException ex) {
                    Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } catch (DoesNotExistOnWMException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownSubarchitectureException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
