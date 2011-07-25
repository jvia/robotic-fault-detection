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
public class CastServer {

    private String castServerPath;
    private Process castServer;
    BufferedReader r;

    public CastServer()
    {
        this("/usr/local/bin/cast-server");
    }

    public CastServer(String castServerPath)
    {
        this.castServerPath = castServerPath;
    }

    public void start()
    {
        try {
            castServer = Runtime.getRuntime().exec(castServerPath);
            showOutput();
        } catch (IOException ex) {
            Logger.getLogger(CastServer.class.getName()).log(Level.SEVERE, "Error running CAST", ex);
        }
    }

    public void stop()
    {
        try {
            r.close();
        } catch (IOException ex) {
            Logger.getLogger(CastServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (castServer != null)
            castServer.destroy();
    }

    public void showOutput()
    {
        new Thread(new Runnable() {

            @Override
            public void run()
            {
                try {
                    r = new BufferedReader(new InputStreamReader(castServer.getInputStream()));
                    String in;
                    while (true) {
                        in = r.readLine();
                        if (in != null)
                            System.out.println("[cast-server] " + in);
                    }
//                    while ((in = r.readLine()) != null)
//                        System.out.println("[cast-server] " + in);
//                    System.out.println("<cast-server> done");
                } catch (IOException ex) {
                    Logger.getLogger(CastServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }

    public static void main(String[] args) throws InterruptedException
    {
        CastServer s = new CastServer();
        s.start();
        s.stop();
    }
}
