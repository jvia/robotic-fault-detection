package helloWorld;

import cast.architecture.ManagedComponent;
import helloWorld.src.java.helloworld.Announcement;
import cast.AlreadyExistsOnWMException;
import helloWorld.src.java.helloworld.Announcement;


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
