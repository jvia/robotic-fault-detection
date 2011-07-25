package updater;

import cast.AlreadyExistsOnWMException;
import cast.architecture.ManagedComponent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is a simple component which adds a random number to working memory 
 * at random intervals.
 * 
 * @author Jeremiah Via <jxv911@cs.bham.ac.uk>
 */
public class Updater extends ManagedComponent {

    Timer timer;
    ScheduledThreadPoolExecutor executor;
    int max = 10000;
    int min = -max;

    public Updater()
    {
        timer = new Timer();
        executor = new ScheduledThreadPoolExecutor(1);
    }

    @Override
    public void run()
    {
        int current = min;

        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run()
            {
                executor.schedule(new Runnable() {

                    @Override
                    public void run()
                    {

                        // Generate a random number
                        UpdateMessage msg = new UpdateMessage((int) (min + Math.random() * (2 * max)));

                        // Attempt to add to memory
                        try {
                            addToWorkingMemory(newDataID(), msg);
                            println(msg.msg);
                        } catch (AlreadyExistsOnWMException ex) {
                            Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }, (long) (Math.random() * 500), TimeUnit.MILLISECONDS);
            }
        }, 0, 500);
    }

    @Override
    public void destroy()
    {
        timer.cancel();
        executor.shutdown();
    }
}
