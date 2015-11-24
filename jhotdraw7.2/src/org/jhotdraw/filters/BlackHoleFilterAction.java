/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.filters;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.action.AbstractSelectedAction;
import org.jhotdraw.samples.svg.figures.SVGImageFigure;

/**
 *
 * @author Marti13/MartinBagge
 */
public class BlackHoleFilterAction extends AbstractSelectedAction {

    public final static String ID = "filter.blackHole";
    private SVGImageFigure originalFigure;
    private BufferedImage originalImage, blackHoleImage;

    public BlackHoleFilterAction(DrawingEditor editor) {
        super(editor);
    }

    public void actionPerformed(ActionEvent ae) {
        final DrawingView view = getView();
        final LinkedList<Figure> figures = new LinkedList<Figure>(view.getSelectedFigures());
        final Figure figure = figures.getFirst();
        applyFilter(figure);

        fireUndoableEditHappened(new AbstractUndoableEdit() {
            @Override
            public String getPresentationName() {
                return labels.getTextProperty(ID);
            }

            @Override
            public void redo() throws CannotRedoException {
                super.redo();
            }

            @Override
            public void undo() throws CannotUndoException {
                super.undo();
            }
        });
    }

    private void applyFilter(Figure figure) {
        if (figure instanceof SVGImageFigure) {
            createBlackHole(figure);
        }
    }

    private void createBlackHole(Figure figure) {
        originalFigure = (SVGImageFigure) figure;
        originalImage = originalFigure.getBufferedImage();
        blackHoleImage = new BlackHole().createFilter(originalImage);
        originalFigure.setBufferedImage(blackHoleImage);
    }

}
