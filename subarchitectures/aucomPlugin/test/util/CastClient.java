package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jeremiah Via <jxv911@cs.bham.ac.uk>
 */
public class CastClient {

    private String castClientPath;
    private String castConfig;
    private Process castClient;

    public CastClient(String castConfig)
    {
        this("/usr/local/bin/cast-client", castConfig);
    }

    public CastClient(String castServerPath, String castConfig)
    {
        this.castClientPath = castServerPath;
    }

    public void start()
    {
        try {
            castClient = Runtime.getRuntime().exec(String.format("%s %s", castClientPath, castConfig));
            showOutput();
        } catch (IOException ex) {
            Logger.getLogger(CastServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void stop()
    {
        if (castClient != null)
            castClient.destroy();
    }

    public void showOutput()
    {
        new Thread(new Runnable() {

            @Override
            public void run()
            {
                try {
                    BufferedReader r = new BufferedReader(new InputStreamReader(castClient.getInputStream()));
                    String in;
                    while((in = r.readLine()) != null)
                        System.out.println("[cast-client] " + in);
                    System.out.println("<cast-client> done");
                } catch (IOException ex) {
                    Logger.getLogger(CastServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }
    
    public static void main(String[] args)
    {
        CastClient c = new CastClient("~/Workspace/robot-fault-detection/subarchitectures/faultConnector/config/fault.cast");
        c.start();
//        Thread.sleep(5000);
    }
}
