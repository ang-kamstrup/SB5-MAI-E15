/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw.action;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Locale;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;
import org.jhotdraw.draw.CompositeFigure;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 * @(#)Grouping.java  2.0.3  2015-11-11
 * Refactored code for the grouping function, the original code is from GroupAction.java.
 * Is used by the classes GroupAction.java and ApplyScaleButton
 * 
 * @author Jens Schwee
 */
public class  Grouping {
    
    /**
     * Function for ungroup figures.
     * Before calling this it is a good idea to call the canUngroup()
     * @param view The view of the GUI, this must be the view where the group to ungroup is on.
     * @param group the group to ungroup
     * @return the Figures there is in the group
     */
    private Collection<Figure> ungroupFigures(DrawingView view, CompositeFigure group) {
        LinkedList<Figure> figures = new LinkedList<Figure>(group.getChildren());
        view.clearSelection();
        group.basicRemoveAllChildren();
        view.getDrawing().basicAddAll(view.getDrawing().indexOf(group), figures);
        view.getDrawing().remove(group);
        view.addToSelection(figures);
        return figures;
    }

    /**
     * Function for grouping figures.
     * @param view The view of the GUI, this must be the view where the figures selected is on
     * @param group the group to add the figures to
     * @param figures the figures to group
     */
    private void groupFigures(DrawingView view, CompositeFigure group, Collection<Figure> figures) {
        Collection<Figure> sorted = view.getDrawing().sort(figures);
        int index = view.getDrawing().indexOf(sorted.iterator().next());
        view.getDrawing().basicRemoveAll(figures);
        view.clearSelection();
        view.getDrawing().add(index, group);
        group.willChange();
        for (Figure f : sorted) {
            group.basicAdd(f);
        }
        group.changed();
        view.addToSelection(group);
    }
    
    /**
     * Group the selected figures
     * Also creates the event handler for redo and undo actions
     * @param localView the view witch holds the figures
     * @param prototype the group to put the figures in
     * @return the undo event for this action
     */
    public UndoableEdit groupAction(DrawingView localView, CompositeFigure prototype)
    {
        final DrawingView view = localView;
        final LinkedList<Figure> ungroupedFigures = new LinkedList<Figure>(view.getSelectedFigures());
        final CompositeFigure group = (CompositeFigure) prototype.clone();
        UndoableEdit edit = new AbstractUndoableEdit() {
            @Override
            public String getPresentationName() {
                return "";//labels.getString("edit.groupSelection.text");
            }

            public void redo() throws CannotRedoException {
                super.redo();
                groupFigures(view, group, ungroupedFigures);
            }

            @Override
            public void undo() throws CannotUndoException {
                super.undo();
                ungroupFigures(view, group);

            }

            @Override
            public boolean addEdit(UndoableEdit anEdit) {
                return super.addEdit(anEdit);
            }
        };
        groupFigures(view, group, ungroupedFigures);
        return edit;    
    }
    
    /**
     * Group the selected figures
     * Also creates the event handler for redo and undo actions
     * @param localView the view witch holds the figures
     * @return the undo event for this action
     */
    public UndoableEdit ungroupAction(DrawingView localView)
    {
        final DrawingView view = localView;
        final CompositeFigure group = (CompositeFigure) localView.getSelectedFigures().iterator().next();
        final LinkedList<Figure> ungroupedFigures = new LinkedList<Figure>();
        UndoableEdit edit = new AbstractUndoableEdit() {
            @Override
            public String getPresentationName() {
                return "";//labels.getString("edit.ungroupSelection.text");
            }

            @Override
            public void redo() throws CannotRedoException {
                super.redo();
                ungroupFigures(view, group);
            }

            @Override
            public void undo() throws CannotUndoException {
                super.undo();
                groupFigures(view, group, ungroupedFigures);
            }
        };
        ungroupedFigures.addAll(ungroupFigures(view, group));
        return edit;
    }
    
    /**
     * Can the selected be ungroups
     * @param localView the view for the figures
     * @param prototype the group to ungrups
     * @return can the selected be ungroup
     */
    protected boolean canUngroup(DrawingView localView, CompositeFigure prototype) {
        return localView != null &&
                localView.getSelectionCount() == 1 &&
                prototype != null &&
                localView.getSelectedFigures().iterator().next().getClass().equals(
                prototype.getClass());
    }
}
