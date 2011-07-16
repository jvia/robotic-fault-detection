package helloworld;

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


public class HelloReader extends ManagedComponent {
    @Override public void start() {
        addChangeFilter(ChangeFilterFactory.createLocalTypeFilter(Announcement.class, WorkingMemoryOperation.ADD),
                        new WorkingMemoryChangeReceiver() {
                            public void workingMemoryChanged(WorkingMemoryChange _wmc) {
                                makeAnnouncement(_wmc);
                            }
                        });
    }


    private void makeAnnouncement(WorkingMemoryChange _wmc) {
        try {
            Announcement ann = getMemoryEntry(_wmc.address, Announcement.class);
            println("I'd like to make an announcement: " + ann.message);
        } catch (DoesNotExistOnWMException e) {
            e.printStackTrace();
        } catch (UnknownSubarchitectureException e) {
            e.printStackTrace();
        }
    }
}
