/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw.action;

import java.awt.event.ActionEvent;
import java.util.Locale;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author Jens Schwee
 */
public class ApplyScaleButton  extends AbstractSelectedAction{

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
    
    public void applyScale()
    {
        DrawingView view = getView();

         for (Figure figure : view.getSelectedFigures()) {
            //Her skal der kaldes scale
             
         }
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
