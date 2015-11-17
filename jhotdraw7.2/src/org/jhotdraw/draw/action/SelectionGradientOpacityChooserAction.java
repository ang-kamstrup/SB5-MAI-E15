package org.jhotdraw.draw.action;

import javax.swing.*;
import java.util.*;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;
import org.jhotdraw.draw.*;
import org.jhotdraw.gui.JAttributeSlider;
import org.jhotdraw.samples.svg.Gradient;
import org.jhotdraw.samples.svg.SVGAttributeKeys;

/**
 * 
 * @author Peter G. Andersen - peand13@student.sdu.dk
 */
public class SelectionGradientOpacityChooserAction extends AttributeAction {
    protected AttributeKey<Gradient> key;
    protected int stop;
    
    protected JAttributeSlider attributeSlider;
    
    /** Creates a new instance. */
    public SelectionGradientOpacityChooserAction(DrawingEditor editor, int stop, JAttributeSlider attributeSlider, AttributeKey<Gradient> key) {
        this(editor, stop, attributeSlider, key, null, null);
    }
    /** Creates a new instance. */
    public SelectionGradientOpacityChooserAction(DrawingEditor editor, int stop, JAttributeSlider attributeSlider, AttributeKey<Gradient> key, Icon icon) {
        this(editor, stop, attributeSlider, key, null, icon);
    }
    /** Creates a new instance. */
    public SelectionGradientOpacityChooserAction(DrawingEditor editor, int stop, JAttributeSlider attributeSlider, AttributeKey<Gradient> key, String name) {
        this(editor, stop, attributeSlider, key, name, null);
    }
    public SelectionGradientOpacityChooserAction(DrawingEditor editor, int stop, JAttributeSlider attributeSlider, final AttributeKey<Gradient> key, String name, Icon icon) {
        this(editor, stop, attributeSlider, key, name, icon, new HashMap<AttributeKey,Object>(), false);
    }
    
    public SelectionGradientOpacityChooserAction(DrawingEditor editor, int stop, JAttributeSlider attributeSlider, final AttributeKey<Gradient> key, String name, Icon icon, Map<AttributeKey,Object> fixedAttributes, boolean chooseColor) {
        super(editor, fixedAttributes, name, icon);
        this.key = key;
        this.stop = stop;
        this.attributeSlider = attributeSlider;
        putValue(AbstractAction.NAME, name);
        putValue(AbstractAction.SMALL_ICON, icon);
        super.setEnabled(true);
    }
    
    protected double getInitialOpacity() {
        double initialOpacity = 1d;
        
        DrawingView v = getEditor().getActiveView();
        if (v != null && v.getSelectedFigures().size() == 1) {
            Figure f = v.getSelectedFigures().iterator().next();
            Gradient g = key.get(f);
            double[] opacities = g.getStopOpacities();
            
            if(opacities.length > stop) {
                initialOpacity = opacities[stop];
            }
        }
        return initialOpacity;
    }
    
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {        
        double initialOpacity = getInitialOpacity();
        double chosenOpacity = attributeSlider.getAttributeValue();
        
        for(final Figure f : getView().getSelectedFigures()) {
            final Gradient newGradient = key.get(f);
            final Gradient oldGradient = key.get(f);

            f.willChange();
            double[] opacities = newGradient.getStopOpacities();
            opacities[stop] = chosenOpacity;
            newGradient.setStops(newGradient.getStopOffsets(), newGradient.getStopColors(), opacities);
            f.setAttribute(SVGAttributeKeys.FILL_GRADIENT, newGradient);

            UndoableEdit edit = new AbstractUndoableEdit() {
                @Override
                public String getPresentationName() {
                    // TODO: Do this properly.
                    return "test"/*labels.getString("edit.ungroupSelection.text")*/;
                }

                @Override
                public void redo() throws CannotRedoException {
                    super.redo();
                    f.setAttribute(SVGAttributeKeys.FILL_GRADIENT, newGradient);
                    getView().repaintHandles();
                }

                @Override
                public void undo() throws CannotUndoException {
                    f.setAttribute(SVGAttributeKeys.FILL_GRADIENT, oldGradient);
                    getView().repaintHandles();
                    super.undo();
                }
            };
            fireUndoableEditHappened(edit);
            getView().repaintHandles();
            f.changed();
        }
    }

    public void selectionChanged(FigureSelectionEvent evt) {
        //setEnabled(getView().getSelectionCount() > 0);
    }
}
