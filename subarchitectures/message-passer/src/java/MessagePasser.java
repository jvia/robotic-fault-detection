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
        println("Message Passer Running");

        int i = 0;
        while (true) {
            IntMessage message = new IntMessage(i++);
            try {
                addToWorkingMemory(newDataID(), message);
            } catch (AlreadyExistsOnWMException e) {
                e.printStackTrace();
            }
        }
    }
}

