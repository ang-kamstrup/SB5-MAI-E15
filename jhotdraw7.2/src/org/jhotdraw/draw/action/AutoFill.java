/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw.action;

import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import org.jhotdraw.draw.AbstractTool;
import org.jhotdraw.draw.BezierFigure;
import org.jhotdraw.draw.CompositeFigure;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.ToolEvent;
import org.jhotdraw.draw.ToolListener;
import org.jhotdraw.geom.BezierPath;
import org.jhotdraw.geom.BezierPath.Node;
import org.jhotdraw.samples.svg.PathTool;
import org.jhotdraw.samples.svg.action.CombineAction;
import org.jhotdraw.samples.svg.figures.SVGBezierFigure;
import org.jhotdraw.samples.svg.figures.SVGFigure;
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
        omega.setNode(omega.getNodeCount()-1,new BezierPath.Node(omega.getStartPoint()));
        
        for (int i = 0; i<omega.getNodeCount();i++){
            System.out.println("node n" + omega.getNode(i) + "x: " + omega.getNode(i).x[0] + "y: "+omega.getNode(i).y[0]);
        }
        
        
        view.getDrawing().add(omega);

    }

//    public void GenPathTest(MouseEvent evt) {
//        final DrawingView view = getView();
//        BezierPath[] list = (BezierPath[]) view.getDrawing().getChildren().toArray();
//        List<GeneralPath> paths = new ArrayList<GeneralPath>();
//        Point2D.Double targetPoint = new Point2D.Double((double) evt.getX(), (double) evt.getY());
//        Point2D.Double nextPoint = new Point2D.Double(targetPoint.x, targetPoint.y);
//        for (int i = 0; i < 1000; i++) {
//            targetPoint.x++;
//            for (BezierPath fig : list) {
//
//                while (fig.outlineContains(targetPoint, 10)) {
//                    nextPoint.x++;
//                    if (fig.outlineContains(nextPoint, 10)) {
//                        targetPoint.x++;
//                        continue;
//                    }
//                    nextPoint.x -= 2;
//                    if (fig.outlineContains(nextPoint, 10)) {
//                        targetPoint.x--;
//                        continue;
//                    }
//                    nextPoint.x++;
//                    nextPoint.y++;
//                    if (fig.outlineContains(nextPoint, 10)) {
//                        targetPoint.y++;
//                        continue;
//                    }
//                    nextPoint.y -= 2;
//                    if (fig.outlineContains(nextPoint, 10)) {
//                        targetPoint.y--;
//                        continue;
//                    }
//                }
//            }
//        }
//    }

//        combiner = new CombineAction(editor);
//        SVGPathFigure group = new SVGPathFigure();
//        combiner.combinePaths(view, group, flatten, 0);
    @Override
    public void mousePressed(MouseEvent evt) {

        fillLogic(evt);
    }

    public void mouseDragged(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void toolStarted(ToolEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void toolDone(ToolEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void areaInvalidated(ToolEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
