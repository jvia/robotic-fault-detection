package updater;

import cast.AlreadyExistsOnWMException;
import cast.architecture.ManagedComponent;
import count.Count;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is a simple component which adds a random number to working memory 
 * at specified intervals.
 * 
 * The component allows for updates to occur on an equal distribution or on
 * a Gaussian distribution.
 * 
 * The syntax:
 *  • "--gaussian mean variance"
 *  • "--equal min max"
 * where mean, variance, min, and max are integer values.
 * 
 * @author Jeremiah Via <jxv911@cs.bham.ac.uk>
 */
public class Updater extends ManagedComponent {

    private static final int MIN = 0;
    private static final int MAX = 5000;
    private int[] vals;
    private boolean running;
    private Distribution dist;
    private Random random;

    private enum Distribution {

        Gaussian, Equal
    }

    /**
     * Construct the updater object.
     */
    public Updater()
    {
        random = new Random();
    }

    /**
     * Configures the Updater to update based on a set distribution. Currently,
     * an equal distribution and a Gaussian distribution are supported.
     * 
     * @param _config command line parameters
     */
    @Override
    protected void configure(Map<String, String> _config)
    {
        if (_config.containsKey("--gaussian")) {
            dist = Distribution.Gaussian;
            vals = parseString(_config.get("--gaussian"));
        } else if (_config.containsKey("--equal")) {
            dist = Distribution.Equal;
            vals = parseString(_config.get("--equal"));
        } else { // will use equal distribution as a default
            dist = Distribution.Equal;
            vals = new int[]{0, 500};
        }
    }

    /**
     * Writes messages to working memory on a time distribution until the 
     * component is shut down.
     */
    @Override
    public void run()
    {
        running = true;
        while (running) {
            // Generate a random number
            UpdateMessage msg = new UpdateMessage((int) (MIN + Math.random() * (2 * MAX)));

            // Simulate resource starvation
            // if (Count.isErrorCondition())
            //     try {
            //     Thread.sleep(2000);
            // } catch (InterruptedException ex) {
            //     Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
            // }
            
            // Attempt to add to memory & then sleep
            try {
                addToWorkingMemory(newDataID(), msg);
                println(msg.msg);
                Thread.sleep(sleepTime());
            } catch (InterruptedException ex) {
                Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
            } catch (AlreadyExistsOnWMException ex) {
                Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Tells the component to stop running.
     */
    @Override
    public void destroy()
    {
        running = false;
    }

    /**
     * Calculate the time required to wait until the next message.
     * 
     * @return the amount of time to sleep.
     */
    private long sleepTime()
    {
        long time;

        switch (dist) {
            case Gaussian:
                time = (long) (vals[0] + (random.nextGaussian() * vals[1]));
                break;
            case Equal:
            default:
                time = vals[0] + random.nextInt(vals[1] - vals[0]);
        }

        if (time < 0)
            time = 0;

        return time;
    }

    /**
     * Get the mean and variance or the min and max values out of the configuration
     * string.
     * 
     * @param s configuration string
     * @return mean and variance OR min and max
     */
    private int[] parseString(String s)
    {
        return new int[]{Integer.parseInt(s.split(" ")[0]),
                         Integer.parseInt(s.split(" ")[1])};
    }
}
