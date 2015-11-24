/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.List;
import java.util.Random;
import junit.framework.Assert;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.geom.BezierPath;
import org.jhotdraw.samples.svg.figures.SVGBezierFigure;
import org.jhotdraw.samples.svg.figures.SVGPathFigure;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;

/**
 *
 * @author Bryan
 */
public class AddPointTest {

    SVGRectFigure rf;
    List<Figure> children;
    BezierPath.Node lowerLeftCorner, upperLeftCorner, upperRightCorner, lowerRightCorner;

    public AddPointTest() {
    }

    @org.junit.BeforeClass
    public static void setUpClass() throws Exception {
    }

    @org.junit.AfterClass
    public static void tearDownClass() throws Exception {
    }

    @org.junit.Before
    public void setUp() throws Exception {
        Random rand = new Random();

        // Create random rectangle
        rf = new SVGRectFigure((double) rand.nextInt(700),(double) rand.nextInt(700),(double) rand.nextInt(700),(double) rand.nextInt(700));

        // Left lower corner
        lowerLeftCorner = new BezierPath.Node(rf.getX(), rf.getY());

        // Left top corner
        upperLeftCorner = new BezierPath.Node(rf.getX(), (rf.getY() + rf.getHeight()));

        // Right top corner
        upperRightCorner = new BezierPath.Node((rf.getX() + rf.getWidth()), (rf.getY() + rf.getHeight()));

        // Right lower corner
        lowerRightCorner = new BezierPath.Node((rf.getX() + rf.getWidth()), rf.getY());

    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    /**
     * Test of handleMouseClick method, of class SVGRectFigure.
     */
    @org.junit.Test
    public void testConvertToPathFigure() {
        System.out.println("Converting rectangle to pathfigure");

        SVGPathFigure pfInstance = new SVGPathFigure();
        pfInstance = rf.convertToPathFigure(rf, pfInstance);

        // Assert Pathfigure only has 1 child
        children = pfInstance.getChildren();
        Assert.assertEquals(1, children.size());

        // Assert that 1 child equals a BezierFigure
        SVGBezierFigure bf = pfInstance.getChild(0);
        Assert.assertEquals((SVGBezierFigure.class), bf.getClass());

        // Assert BezierFigure has 4 nodes representing rectangle corners
        Assert.assertEquals(4, bf.getNodeCount());


        // Assert Bezierfigure nodes equal Rectangle corners
        // Lower left corner
        Assert.assertEquals(lowerLeftCorner.toString(), bf.getNode(0).toString());

        // Upper left corner
        Assert.assertEquals(upperLeftCorner.toString(), bf.getNode(1).toString());

        // Upper right corner
        Assert.assertEquals(upperRightCorner.toString(), bf.getNode(2).toString());

        // Lower right corner
        Assert.assertEquals(lowerRightCorner.toString(), bf.getNode(3).toString());

        // Assert Pathfigure bounds equals Rectangle bounds
        Assert.assertEquals(rf.getBounds(), pfInstance.getBounds());

    }
}
