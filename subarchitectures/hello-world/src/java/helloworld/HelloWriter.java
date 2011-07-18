package helloworld;

import cast.architecture.ManagedComponent;
import helloworld.Announcement;
import cast.AlreadyExistsOnWMException;


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
