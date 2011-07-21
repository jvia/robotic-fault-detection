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
import returnMessage.Message;

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
            addToWorkingMemory(dataID, new Message(Integer.MIN_VALUE));
        } catch (AlreadyExistsOnWMException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        ChangeFilterFactory.createSourceFilter(Message.class, "");
        
        addChangeFilter(ChangeFilterFactory.createLocalTypeFilter(Message.class, WorkingMemoryOperation.OVERWRITE),
                new WorkingMemoryChangeReceiver() {

                    @Override
                    public void workingMemoryChanged(WorkingMemoryChange _wmc) {
                        updateMonitor(_wmc);
                    }
                });
    }

    private void updateMonitor(WorkingMemoryChange _wmc) {
        try {

            Message msg = getMemoryEntry(_wmc.address, Message.class);
            Message max = getMemoryEntry(dataID, Message.class);

            if (msg.msg > max.msg) {
                max.msg = msg.msg;
                println("Max value monitored :: " + max.msg);

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
