package messagepasser;

import cast.architecture.ManagedComponent;
//Factory functions for change filters
import cast.architecture.ChangeFilterFactory;
//Classes for receiving and managing changes
import cast.architecture.WorkingMemoryChangeReceiver;
import cast.cdl.WorkingMemoryChange;
import cast.cdl.WorkingMemoryOperation;
//For exceptions thrown by getMemoryEntry
import cast.UnknownSubarchitectureException;
import cast.DoesNotExistOnWMException;
import passedmessage.IntMessage;


public class MessagePassee extends ManagedComponent {

    @Override public void start() {
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
            println("I read: " + message.message);
            // TODO look up how to remove data from working memory
        } catch (DoesNotExistOnWMException e) {
            e.printStackTrace();
        } catch (UnknownSubarchitectureException e) {
            e.printStackTrace();
        }
    }
}