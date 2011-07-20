package messagepasser;

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
import passedmessage.IntMessage;

public class MessagePassee extends ManagedComponent {

    @Override
    public void start() {
        addChangeFilter(ChangeFilterFactory.createLocalTypeFilter(IntMessage.class, WorkingMemoryOperation.ADD),
                new WorkingMemoryChangeReceiver() {

                    public void workingMemoryChanged(WorkingMemoryChange _wmc) {
                        makeAnnouncement(_wmc);
                    }
                });
    }

    private void makeAnnouncement(WorkingMemoryChange _wmc) {
        try {
            IntMessage message = getMemoryEntry(_wmc.address, IntMessage.class);
            println("Msg :: " + message.message +
                    " Mem Size :: " + this.getWorkingMemoryEntries(IntMessage.class).length);

            // remove received message from memory
            this.deleteFromWorkingMemory(_wmc.address);
        } catch (SubarchitectureComponentException ex) {
            Logger.getLogger(MessagePassee.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}