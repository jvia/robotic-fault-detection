package statusUpdater;

import cast.AlreadyExistsOnWMException;
import cast.ConsistencyException;
import cast.DoesNotExistOnWMException;
import cast.PermissionException;
import cast.architecture.ManagedComponent;
import java.util.logging.Level;
import java.util.logging.Logger;
import returnMessage.Message;

/**
 *
 * @author jxv911
 */
public class Updater extends ManagedComponent {

    Message msg;
    String updateID;

    @Override
    protected void start() {
        super.start();
        updateID = newDataID();
        msg = new Message(0);

        try {
            addToWorkingMemory(updateID, msg);
        } catch (AlreadyExistsOnWMException ex1) {
            Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }

    @Override
    protected void runComponent() {
        super.runComponent();

        int max = 10000;
        int i = 0;

        while (i++ < 20) {
            try {
                int random = (int) Math.round(Math.random() * max);
//                println("Generated :: " + random);
                
                msg = new Message(random);
                overwriteWorkingMemory(updateID, msg);
            } catch (DoesNotExistOnWMException ex) {
                Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ConsistencyException ex) {
                Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
            } catch (PermissionException ex) {
                Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
            }


        }

        println("Done");
        destroy();

    }
}