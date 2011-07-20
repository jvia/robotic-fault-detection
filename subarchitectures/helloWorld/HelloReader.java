package helloWorld;

import cast.architecture.ManagedComponent;
import cast.architecture.ChangeFilterFactory;
import cast.architecture.WorkingMemoryChangeReceiver;
import cast.cdl.WorkingMemoryChange;
import cast.cdl.WorkingMemoryOperation;
import cast.UnknownSubarchitectureException;
import cast.DoesNotExistOnWMException;

/**
 * 
 * @author Jeremiah Via <jxv911@cs.bham.ac.uk>
 */
public class HelloReader extends ManagedComponent {

    /**
     * 
     */
    @Override
    public void start() {
        // register call back method to be notified when Announcements are 
        // addedd to working memory
        addChangeFilter(ChangeFilterFactory.createLocalTypeFilter(
                Announcement.class,
                WorkingMemoryOperation.ADD),
                new WorkingMemoryChangeReceiver() {

                    @Override
                    public void workingMemoryChanged(WorkingMemoryChange _wmc) {
                        makeAnnouncement(_wmc);
                    }
                });
    }

    /**
     * Displays contents of announcement message and then kills the component.
     * 
     * @param _wmc the WorkingMemoryChange created by event
     */
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
