/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw.action;

import java.util.LinkedList;
import java.util.List;
import org.jhotdraw.draw.DefaultDrawing;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.samples.svg.figures.SVGGroupFigure;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import org.junit.*;

/**
 *
 * @author Jens Schwee
 */
public class GroupingTest {

    Grouping g = new Grouping();
    DrawingView dv = new DefaultDrawingView();
    List<Figure> figList = new LinkedList<Figure>();
    DefaultDrawing draw = new DefaultDrawing();

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        figList.add(new SVGRectFigure());
        dv.setDrawing(draw);

    }

    @After
    public void tearDown() {
        dv.clearSelection();
        figList.clear();
    }

    @Test
    public void testGrouping() {

        figList.add(new SVGRectFigure(10, 10, 10, 10));
        draw.addAll(figList);

        dv.addToSelection(figList);
        Assert.assertTrue((dv.getSelectionCount() == 2) == true);

        g.groupAction(dv, new SVGGroupFigure());

        Assert.assertTrue((dv.getSelectionCount() == 1) == true);

        g.groupAction(dv, new SVGGroupFigure());
        dv.clearSelection();

        SVGRectFigure fig = new SVGRectFigure();
        draw.add(fig);
        dv.addToSelection(fig);

        fig = new SVGRectFigure();
        draw.add(fig);
        dv.addToSelection(fig);

        g.groupAction(dv, new SVGGroupFigure());

        dv.selectAll();
        
        g.groupAction(dv, new SVGGroupFigure());

        Assert.assertTrue (dv.getSelectionCount() == 1);

    }

    @Test
    public void testUngrouping() {

        dv.addToSelection(figList);

        //Error do to selected figure not group
        Assert.assertFalse(g.canUngroup(dv, new SVGGroupFigure()));

        figList.add(new SVGRectFigure(10, 10, 10, 10));
        dv.addToSelection(figList);

        //Error do to selected 2 figures
        Assert.assertFalse(g.canUngroup(dv, new SVGGroupFigure()));

        //Error do to null parameter
        Assert.assertFalse(g.canUngroup(dv, null));

        draw.addAll(figList);
        g.groupAction(dv, new SVGGroupFigure());

        //Test if the canUngroup retuens true if there is selected a group and only one group
        Assert.assertTrue(g.canUngroup(dv, new SVGGroupFigure()));

        //Tests the ungroup action
        g.ungroupAction(dv);
        Assert.assertTrue(dv.getSelectionCount() == 2);

        g.groupAction(dv, new SVGGroupFigure());
        dv.clearSelection();

        SVGRectFigure fig = new SVGRectFigure();
        draw.add(fig);
        dv.addToSelection(fig);

        fig = new SVGRectFigure();
        draw.add(fig);
        dv.addToSelection(fig);

        g.groupAction(dv, new SVGGroupFigure());

        dv.selectAll();

        //test if 2 groups can be can ungroup
        Assert.assertFalse(g.canUngroup(dv, new SVGGroupFigure()));

    }
}
