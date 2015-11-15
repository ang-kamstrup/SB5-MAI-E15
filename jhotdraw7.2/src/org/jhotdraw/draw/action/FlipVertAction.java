package org.jhotdraw.draw.action;

import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.LinkedList;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import org.jhotdraw.draw.CompositeFigure;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.GroupFigure;

/**
 * FlipVertAction.
 * 
 * @author Simon Hansen
 */
public class FlipVertAction extends AbstractSelectedAction{
    public final static String ID = "edit.flipvert";
    
    /** Creates a new instance. */
    public FlipVertAction(DrawingEditor editor) {
        super(editor);
        labels.configureAction(this, ID);
    }

    public void actionPerformed(ActionEvent e) {
        final DrawingView view = getView();
        
        flipVertically(view);
        
        fireUndoableEditHappened(new AbstractUndoableEdit() {
            @Override
            public String getPresentationName() {
                return labels.getTextProperty(ID);
            }
            @Override
            public void redo() throws CannotRedoException {
                super.redo();
                FlipVertAction.flipVertically(view);
            }
            @Override
            public void undo() throws CannotUndoException {
                super.undo();
                FlipVertAction.flipVertically(view);
            }
        });
    }
    
    private static void flipVertically(DrawingView view) {
        AffineTransform tx = null;
        Point2D.Double p0 = null;
        LinkedList<Figure> figures = new LinkedList<Figure>(view.getSelectedFigures());
        if(view.getSelectionCount() > 1) {
            /**
             * Grouping up figures to flip together, instead of flipping each individually
             * Feels like a more expected result, especially when creating symmetrical designs
             */
            Collection<Figure> sorted = view.getDrawing().sort(figures);
            CompositeFigure group = new GroupFigure();
            int index = view.getDrawing().indexOf(sorted.iterator().next());
            view.getDrawing().basicRemoveAll(figures);
            view.clearSelection();
            view.getDrawing().add(index, group);
            group.willChange();
            for(Figure f : sorted) {
                group.basicAdd(f);
            }
            tx = new AffineTransform();
            p0 = new Point2D.Double(group.getBounds().x, group.getBounds().y);
                
            /** Flips the figure and then moves it back to its original position */
            tx.translate(p0.x, p0.y+group.getBounds().getHeight());
            tx.scale(1, -1);
            tx.translate(-group.getBounds().x, -group.getBounds().y);
                
            group.transform(tx);
            group.changed();

            /** Destroy group and split figures into individuals again */
            group.basicRemoveAllChildren();
            view.getDrawing().basicAddAll(view.getDrawing().indexOf(group), figures);
            view.getDrawing().remove(group);
            view.addToSelection(figures);
        } 
        else {
            for(Figure f : figures) {
                tx = new AffineTransform();
                p0 = new Point2D.Double(f.getBounds().x, f.getBounds().y);
                
                tx.translate(p0.x, p0.y+f.getBounds().getHeight());
                tx.scale(1, -1);
                tx.translate(-f.getBounds().x, -f.getBounds().y);
                
                f.willChange();
                f.transform(tx);
                f.changed();
            }  
        }
    }
}
