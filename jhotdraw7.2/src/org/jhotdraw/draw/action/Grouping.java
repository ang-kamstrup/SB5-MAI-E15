/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw.action;

import java.util.Collection;
import java.util.LinkedList;
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
 *
 * @author Jens Schwee
 */
public class  Grouping {
    
    public Collection<Figure> ungroupFigures(DrawingView view, CompositeFigure group) {
        LinkedList<Figure> figures = new LinkedList<Figure>(group.getChildren());
        view.clearSelection();
        group.basicRemoveAllChildren();
        view.getDrawing().basicAddAll(view.getDrawing().indexOf(group), figures);
        view.getDrawing().remove(group);
        view.addToSelection(figures);
        return figures;
    }

    public void groupFigures(DrawingView view, CompositeFigure group, Collection<Figure> figures) {
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
                ungroupFigures(view, group);
                super.undo();
            }

            @Override
            public boolean addEdit(UndoableEdit anEdit) {
                return super.addEdit(anEdit);
            }
        };
        groupFigures(view, group, ungroupedFigures);
        return edit;    
    }
    
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
                groupFigures(view, group, ungroupedFigures);
                super.undo();
            }
        };
        ungroupedFigures.addAll(ungroupFigures(view, group));
        return edit;
    }
    
    
    protected boolean canUngroup(DrawingView localView, CompositeFigure prototype) {
        return localView != null &&
                localView.getSelectionCount() == 1 &&
                prototype != null &&
                localView.getSelectedFigures().iterator().next().getClass().equals(
                prototype.getClass());
    }
}
