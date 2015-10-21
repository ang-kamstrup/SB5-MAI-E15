/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw.action;

import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.Locale;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.samples.svg.figures.SVGGroupFigure;
import org.jhotdraw.samples.svg.gui.SelectionComponentDisplayer;
import org.jhotdraw.util.ResourceBundleUtil;
import sun.java2d.loops.ScaledBlit;

/**
 *
 * @author Jens Schwee
 */
public class ApplyScaleButton extends AbstractSelectedAction {

    private ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels", Locale.getDefault());

    public ApplyScaleButton(DrawingEditor editor) {
        super(editor);
        labels.configureAction(this, "edit.applyScale");
        setEnabled(true);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Test");
        applyScale();
        setEnabled(true);
    }

    public void applyScale() {
        DrawingView view = getView();

        //Get the area the seleceted figure is in
        double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE, minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;
        for (Figure figure : view.getSelectedFigures()) {
            if(figure.getDrawingArea().width + figure.getDrawingArea().x> maxX){
                maxX = figure.getDrawingArea().width + + figure.getDrawingArea().x;
            }
            
            if(figure.getDrawingArea().x < minX){
                minX = figure.getDrawingArea().x ;
            }
            
            if(figure.getDrawingArea().height + figure.getDrawingArea().y > maxY){
                maxY = figure.getDrawingArea().height + figure.getDrawingArea().y;
            }
            
            if(figure.getDrawingArea().y  < minY){
                minY = figure.getDrawingArea().y ;
            }
        }
        
        //Add the scale area
        
        
        
        System.out.println(minX + " " + maxX + " " +minY + " " + maxY  );
    }

    /*@SuppressWarnings("unchecked")
     public void applyAttributes() {
     DrawingEditor editor = getEditor();

     CompositeEdit edit = new CompositeEdit(labels.getString("edit.applyAttrbutes.text"));
     DrawingView view = getView();
     view.getDrawing().fireUndoableEditHappened(edit);

     for (Figure figure : view.getSelectedFigures()) {
     figure.willChange();
     for (Map.Entry<AttributeKey, Object> entry : editor.getDefaultAttributes().entrySet()) {
     if (!excludedAttributes.contains(entry.getKey())) {
     entry.getKey().basicSet(figure, entry.getValue());
     }
     }
     figure.changed();
     }
     view.getDrawing().fireUndoableEditHappened(edit);
     }

     public void selectionChanged(FigureSelectionEvent evt) {
     setEnabled(getView().getSelectionCount() == 1);
     }
     */
}
