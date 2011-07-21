package updater;

import cast.AlreadyExistsOnWMException;
import cast.architecture.ManagedComponent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Jeremiah Via <jxv911@cs.bham.ac.uk>
 */
public class Updater extends ManagedComponent{

    int max = 10000;
    int min = -max;
    
    @Override
    protected void start()
    {
        int current = min;
        
        for (int i = 0; i < 20; i++) {
            // Sleep for a random amount of time
            try {
                Thread.sleep((long) (Math.random() * 1000.0));
            } catch (InterruptedException ex) {
                Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // Generate a random number
            UpdateMessage msg = new UpdateMessage((int) (min + Math.random() * (2 * max)));
            
            // Attempt to add to memory
            try {
                addToWorkingMemory(newDataID(), msg);
                println(String.format("Update %3d :: %5d", i, msg.msg));
            } catch (AlreadyExistsOnWMException ex) {
                Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
}
