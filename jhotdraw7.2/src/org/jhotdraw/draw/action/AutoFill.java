/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw.action;

import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.jhotdraw.draw.AbstractTool;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.ToolEvent;
import org.jhotdraw.draw.ToolListener;
import org.jhotdraw.geom.BezierPath;
import org.jhotdraw.samples.svg.action.CombineAction;
import org.jhotdraw.samples.svg.figures.SVGBezierFigure;
import org.jhotdraw.samples.svg.figures.SVGPathFigure;

/**
 *
 * @author Mias
 */
public class AutoFill extends AbstractTool implements ToolListener {

    public static String ID = "AutoFill"; //TODO This can't be right.
    public static CombineAction combiner;

    public AutoFill() {
    }

    public void fillLogic(MouseEvent evt) {
        final DrawingView view = getView();
        view.selectAll();
        final Set<Figure> list = view.getSelectedFigures();

        List<SVGPathFigure> flatten = new ArrayList<SVGPathFigure>();

        for (Figure fig : list) {
            if (fig.contains(view.viewToDrawing(evt.getPoint()))) {
                flatten.add((SVGPathFigure) fig);
            }
        }
        try{
            Area alpha = new Area(flatten.get(0).getPath().createTransformedShape(null));
        
            for (int i = 1; i < flatten.size(); i++) {
                Area temp = new Area(flatten.get(i).getPath());
    //            alpha.intersect(new Area(flatten.get(i).getPath().createTransformedShape(null)));
                alpha.intersect(temp);
            }
            SVGBezierFigure omega = new SVGBezierFigure();
            //BezierPath beta = alpha.getPathIterator(null).currentSegment(coords);
            PathIterator iter = alpha.getPathIterator(null);
            while(!iter.isDone()){
                double[] location = new double[6];
                iter.currentSegment(location);
                BezierPath.Node node = new BezierPath.Node();
                node.x[0] = location[0];
                node.x[1] = location[2];
                node.x[2] = location[4];
                node.y[0] = location[1];
                node.y[1] = location[3];
                node.y[2] = location[5];
                omega.addNode(node);

                iter.next();
            }
            omega.setNode(omega.getNodeCount()-1, omega.getNode(0));
            view.getDrawing().add(omega);
            view.clearSelection();
            view.addToSelection(omega);
        }catch(ClassCastException ex){
            System.out.println("Currently, this feature only works with the scribble tool");
        }catch(IndexOutOfBoundsException ex){
            System.out.println("This feature only works inside figures");
        }
    }

    @Override
    public void mousePressed(MouseEvent evt) {

        fillLogic(evt);
    }

    public void mouseDragged(MouseEvent e) {
        System.out.println("You tried dragging the mouse! Why would that make sense?");
    }

    public void toolStarted(ToolEvent event) {
        
    }

    public void toolDone(ToolEvent event) {
        System.out.println("Job's done!");
    }

    public void areaInvalidated(ToolEvent e) {
        
    }
}
