package helloWorld;

import cast.architecture.ManagedComponent;
import cast.AlreadyExistsOnWMException;

/**
 * A simple component which displays hello world and then exits.
 * 
 * @author Jeremiah Via <jxv911@cs.bham.ac.uk>
 */
public class HelloWriter extends ManagedComponent {
    @Override protected void runComponent() {
        println("Look out world, here I come...");
        Announcement ann = new Announcement("Hello World!");
        try {
            addToWorkingMemory(newDataID(), ann);
        } catch (AlreadyExistsOnWMException e) {
            e.printStackTrace();
        }
        
    }
}
