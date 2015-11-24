/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Rectangle;
import org.jhotdraw.samples.svg.figures.StarFigure;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mads
 */
public class StarFigureTest {
    
    StarFigure star;
    Rectangle bounds;
    
    public StarFigureTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        star = new StarFigure(10,10,20,10);
        bounds = new Rectangle(10,10,20,40);
    }
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void testStarBounds() {
         
         Assert.assertEquals(bounds.x,star.getX());
         Assert.assertEquals(bounds.y,star.getY());
         Assert.assertEquals(bounds.height,star.getR()*2);
         Assert.assertEquals(bounds.width,star.getInnerR()*2);
     }
}