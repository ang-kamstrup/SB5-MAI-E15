package org.jhotdraw.filters;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
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
 * @author Dan/Kyzaq
 */
public class GaussianBlurFilterAction extends AbstractSelectedAction {
    public final static String ID = "filter.gaussianBlur";
    private SVGImageFigure imageFigure, blurredFigure;
    private BufferedImageOp blurOperation;
    private BufferedImage figureToBlur, blurredImage;

    public GaussianBlurFilterAction(DrawingEditor editor) {
        super(editor);
    }
    
    public void actionPerformed(ActionEvent e) {
        final DrawingView view = getView();
        final LinkedList<Figure> figures = new LinkedList<Figure>(view.getSelectedFigures());
        final Figure figure = figures.getFirst();
        blurImage(figure);
        
        fireUndoableEditHappened(new AbstractUndoableEdit() {
            @Override
            public String getPresentationName() {
            return labels.getTextProperty(ID);
            }
            @Override
            public void redo() throws CannotRedoException {
                super.redo();
                blurImage(figure);
            }
            @Override
            public void undo() throws CannotUndoException {
                super.undo();
                blurImage(figure);
            }
        }
    );}
    
    private void blurImage(Figure figure){
        try {
            blurOperation(figure);
        } catch (Exception ClassCastException){
            System.out.println("Selected figure must be an image");
        }
    }

    private void blurOperation(Figure figure) {
        imageFigure = (SVGImageFigure) figure;
        figureToBlur = imageFigure.getBufferedImage();
        blurOperation = new ConvolveOp(GaussianKernel.createGaussianKernel());
        blurredImage = blurOperation.filter(figureToBlur, null);
        imageFigure.setBufferedImage(blurredImage);
    }
    
}
