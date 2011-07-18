package messagepasser;

import cast.architecture.ManagedComponent;
import cast.AlreadyExistsOnWMException;
import passedmessage.IntMessage;

/**
 *
 */
public class MessagePasser extends ManagedComponent {

    
    @Override
    protected void runComponent() {
        int i = 1;
        while (i < 20) {
            
            try {
                Thread.sleep(500L);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            
            // Create a new message
            IntMessage message = new IntMessage(i++);
            
            
            try {
                addToWorkingMemory(newDataID(), message);
            } catch (AlreadyExistsOnWMException e) {
                e.printStackTrace();
            }
        }
    }
    
}
