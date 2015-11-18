/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import org.jhotdraw.app.PrintableView;
import org.jhotdraw.app.action.PrintFromCanvasAction;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.samples.svg.SVGView;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Jesper
 */
public class PrintFromCanvasActionTest {
    private PrintFromCanvasAction instance;

    
    public PrintFromCanvasActionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new PrintFromCanvasAction();
    }
    
    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Test of actionPerformed method, of class PrintFromCanvasAction.
     */
    @Test
    public void testActionPerformed() {
        System.out.println("actionPerformed");
        ActionEvent ae = null;
        instance = new PrintFromCanvasAction();
        instance.actionPerformed(ae);
        
        assertEquals(instance.isEnabled(), true);
    }

    /**
     * Test of areaToPrint method, of class PrintFromCanvasAction.
     */
    @Test
    public void testAreaToPrint() {
        System.out.println("areaToPrint");
        //Simulates a chosen rectangle.
        Rectangle rectangle = new Rectangle (300,300, 500, 400);
        
        DrawingView view = null;
        instance = new PrintFromCanvasAction();
        instance.areaToPrint(rectangle, view);
        
        assertTrue(instance.getPrint() != null);
        assertTrue(instance.getImage() != null);
    }
    
    /**
     * Test if view is instance of printableview then the print function is enabled.
     */
    @Test
    public void testPrintableView(){
        SVGView view = new SVGView();
        assertTrue(view instanceof PrintableView);
    }
}
