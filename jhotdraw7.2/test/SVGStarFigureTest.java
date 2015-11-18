/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Point;
import java.awt.geom.Point2D;
import junit.framework.Assert;
import org.jhotdraw.samples.svg.figures.SVGStarFigure;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mads
 */
public class SVGStarFigureTest {
    SVGStarFigure figure;
    Point2D.Double point;
    public SVGStarFigureTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        figure = new SVGStarFigure(20, 20, 20, 10);
        point = new Point2D.Double(21,21);
    }
    
    @After
    public void tearDown() {
    }
     @Test
     public void hitTest() {
         Assert.assertTrue(figure.contains(point));
     }
}
