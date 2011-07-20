package returnMessage;

import cast.SubarchitectureComponentException;
import cast.architecture.ChangeFilterFactory;
import cast.architecture.ManagedComponent;
import cast.architecture.WorkingMemoryChangeReceiver;
import cast.cdl.WorkingMemoryChange;
import cast.cdl.WorkingMemoryOperation;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Receiver extends ManagedComponent {

    @Override
    protected void start() {
        super.start();
        println("Initializing Sender");

        addChangeFilter(ChangeFilterFactory.createLocalTypeFilter(Message.class, WorkingMemoryOperation.ADD),
                new WorkingMemoryChangeReceiver() {

                    @Override
                    public void workingMemoryChanged(WorkingMemoryChange _wmc) {
                        makeAnnouncement(_wmc);
                    }
                });
    }

    private void makeAnnouncement(WorkingMemoryChange _wmc) {
        try {

            Message rm = getMemoryEntry(_wmc.address, Message.class);
            println("Received :: " + rm.msg + " <<>> Memory :: " + this.getWorkingMemoryEntries(Message.class).length);

            // Invert message
            rm = new Message(-rm.msg);
            overwriteWorkingMemory(_wmc.address, rm);

        } catch (SubarchitectureComponentException ex) {
            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}