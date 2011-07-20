package sendReturn;

import cast.AlreadyExistsOnWMException;
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

public class Sender extends ManagedComponent {

    @Override
    protected void start() {
        super.start();
        println("Initializing Sender");

        addChangeFilter(ChangeFilterFactory.createLocalTypeFilter(ReturnMessage.class, WorkingMemoryOperation.OVERWRITE),
                new WorkingMemoryChangeReceiver() {

                    @Override
                    public void workingMemoryChanged(WorkingMemoryChange _wmc) {
                        makeAnnouncement(_wmc);
                    }
                });
    }

    @Override
    protected void runComponent() {
        println("Running Sender");

        int msg = 1;
        while (msg < 20) {
            
            try {
                Thread.sleep(500L);
                ReturnMessage rm = new ReturnMessage(msg++);
                addToWorkingMemory(newDataID(), rm);
            } catch (InterruptedException ex) {
                Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
            } catch (AlreadyExistsOnWMException ex) {
                Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void makeAnnouncement(WorkingMemoryChange _wmc) {
        try {
            ReturnMessage rm = getMemoryEntry(_wmc.address, ReturnMessage.class);
            println("Returned :: " + rm.message + " :: Now deleting...");
            deleteFromWorkingMemory(_wmc.address);
            
        } catch (PermissionException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DoesNotExistOnWMException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownSubarchitectureException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}