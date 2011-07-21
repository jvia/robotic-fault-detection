package messagePasser;

import cast.SubarchitectureComponentException;
import cast.architecture.ManagedComponent;
//Factory functions for change filters
import cast.architecture.ChangeFilterFactory;
//Classes for receiving and managing changes
import cast.architecture.WorkingMemoryChangeReceiver;
import cast.cdl.WorkingMemoryChange;
import cast.cdl.WorkingMemoryOperation;
//For exceptions thrown by getMemoryEntry
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessagePassee extends ManagedComponent {

    @Override
    public void start() {
        addChangeFilter(ChangeFilterFactory.createLocalTypeFilter(Message.class, WorkingMemoryOperation.ADD),
                new WorkingMemoryChangeReceiver() {

                    public void workingMemoryChanged(WorkingMemoryChange _wmc) {
                        makeAnnouncement(_wmc);
                    }
                });
    }

    private void makeAnnouncement(WorkingMemoryChange _wmc) {
        try {
            Message message = getMemoryEntry(_wmc.address, Message.class);
            println("Msg :: " + message.payload +
                    " Mem Size :: " + this.getWorkingMemoryEntries(Message.class).length);

            // remove received message from memory
            this.deleteFromWorkingMemory(_wmc.address);
        } catch (SubarchitectureComponentException ex) {
            Logger.getLogger(MessagePassee.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}