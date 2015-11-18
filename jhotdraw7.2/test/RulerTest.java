/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import org.jhotdraw.samples.svg.gui.Ruler;

/**
 *
 * @author Fjessin
 */
public class RulerTest {

    private Ruler ruler;    
    
    public RulerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws AWTException {
         ruler = new Ruler(0, true);
    }
    
    @After
    public void tearDown() {
       }
    
    @Test
    public void testMetric() {
        assertTrue(ruler.isMetric);
        ruler.isMetric = false;
        assertFalse(ruler.isMetric);   
    }
    
    @Test
    public void testOrientation() {
        assertTrue(ruler.getOrientation());
        ruler.orientation = 1;
        assertFalse(ruler.getOrientation());
    }
}
