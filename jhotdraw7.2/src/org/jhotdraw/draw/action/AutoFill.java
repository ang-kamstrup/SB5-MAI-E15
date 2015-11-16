/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw.action;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
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
import org.jhotdraw.samples.svg.action.CombineAction;
import org.jhotdraw.samples.svg.figures.SVGPathFigure;

/**
 *
 * @author Mias
 */
public class AutoFill extends AbstractTool implements ToolListener{

    public static String ID = "AutoFill"; //TODO This can't be right.
    public static CombineAction combiner;
    public AutoFill(){
        
        
    }
    
    public void fillLogic(MouseEvent evt){
        final DrawingView view = getView();
        view.selectAll();
        final Set<Figure> list = view.getSelectedFigures();
        
        List<Figure> flatten = new ArrayList<Figure>();
        List<Figure> fugure = new ArrayList<Figure>();
        for(Figure fig : list){
            if(fig.contains(view.viewToDrawing(evt.getPoint()))){
                flatten.add(fig);
            }else{
                view.removeFromSelection(fig);
                fugure.add(fig);
            }
        }
        
        
        
    }
        
        
//        combiner = new CombineAction(editor);
//        SVGPathFigure group = new SVGPathFigure();
//        combiner.combinePaths(view, group, flatten, 0);
    
    @Override
    public void mousePressed(MouseEvent evt){
        
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
