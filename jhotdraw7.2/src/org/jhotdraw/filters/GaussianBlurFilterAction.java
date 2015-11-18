package org.jhotdraw.filters;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import org.jhotdraw.app.JHotDrawFeatures;
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
    private SVGImageFigure imageFigure;
    private BufferedImageOp blurOperation;
    private BufferedImage figureToBlur, blurredImage;
    private int blurAmountRequest;
    private final String promptText = "Insert preferred amount of blur:";

    public GaussianBlurFilterAction(DrawingEditor editor) {
        super(editor);
    }
    
    @FeatureEntryPoint(JHotDrawFeatures.GAUSSIAN_BLUR)
    public void actionPerformed(ActionEvent e) {
        final DrawingView view = getView();
        final LinkedList<Figure> figures = new LinkedList<Figure>(view.getSelectedFigures());
        final Figure figure = figures.getFirst();
        
        blurAmountPrompt();
        
        blurImageInThread(figure);
    }

    private void blurAmountPrompt() throws NumberFormatException, HeadlessException {
        blurAmountRequest = Integer.parseInt( JOptionPane.showInputDialog(promptText));
    }

    private void blurImageInThread(final Figure figure) {
        Thread t = new Thread(new Runnable(){
            public void run(){
                blurImage(figure);
            }
        });
        t.start();
        fireUndoableEdit();
    }
    
    private void blurImage(Figure figure){
        try {
            for (int i = 0; i < blurAmountRequest; i++) {
                blurOperation(figure);
            }
        } catch (ClassCastException ex){
            System.out.println("Selected figure must be an image : "+ex);
        }
    }

    private void blurOperation(Figure figure) {
        imageFigure = (SVGImageFigure) figure;
        figureToBlur = imageFigure.getBufferedImage();
        blurOperation = new ConvolveOp(GaussianKernel.createKernel());
        blurredImage = blurOperation.filter(figureToBlur, null);
        imageFigure.setBufferedImage(blurredImage);
    }
    
    private void fireUndoableEdit() {
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
}
