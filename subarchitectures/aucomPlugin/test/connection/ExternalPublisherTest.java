/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jeremiah Via <jxv911@cs.bham.ac.uk>
 */
public class ExternalPublisherTest {
    
    public ExternalPublisherTest()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }

    /**
     * Test of start method, of class ExternalPublisher.
     */
    @Test
    public void testStart()
    {
        System.out.println("start");
        ExternalPublisher instance = new ExternalPublisher();
        instance.start();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of destroy method, of class ExternalPublisher.
     */
    @Test
    public void testDestroy()
    {
        System.out.println("destroy");
        ExternalPublisher instance = new ExternalPublisher();
        instance.destroy();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
