package messagePasser;

import cast.architecture.ManagedComponent;
import cast.AlreadyExistsOnWMException;
import returnMessage.Message;

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
            Message message = new Message(i++);
            
            
            try {
                addToWorkingMemory(newDataID(), message);
            } catch (AlreadyExistsOnWMException e) {
                e.printStackTrace();
            }
        }
    }
    
}
