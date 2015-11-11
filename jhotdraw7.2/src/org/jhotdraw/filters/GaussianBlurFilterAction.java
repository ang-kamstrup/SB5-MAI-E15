package org.jhotdraw.filters;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.AbstractAction;
import org.jhotdraw.app.Application;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.action.AbstractSelectedAction;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author Dan/Kyzaq
 */
public class GaussianBlurFilterAction extends AbstractSelectedAction {
    public final static String ID = "filter.gaussianBlur";
    private BufferedImage blurredImage;
    private Figure blurredFigure;

    public GaussianBlurFilterAction(DrawingEditor editor) {
        super(editor);
    }
    
    public void actionPerformed(ActionEvent e) {
        final DrawingView view = getView();
        final Figure figure = (Figure) view.getSelectedFigures();
        //final LinkedList<Figure> figures = new LinkedList<Figure>(view.getSelectedFigures());
        blurImage(view, figure);
    }
    
    private void blurImage(DrawingView view, Figure figure){
        BufferedImageOp elementToBlur = (BufferedImageOp) figure;
        elementToBlur = new ConvolveOp(GaussianKernel.createGaussianKernel());
        blurredImage = elementToBlur.filter(blurredImage, null);
        blurredFigure = (Figure) blurredImage;
        blurredFigure.changed();
        
        //return blurredImage;
        //Test: Receive a figure, end with an image
        //blurredImage.createGraphics()
    }
    
    /*
     * final DrawingView view = getView();
        final LinkedList<Figure> figures = new LinkedList<Figure>(view.getSelectedFigures());
    public static void bringToFront(DrawingView view, Collection<Figure> figures) {
        Drawing drawing = view.getDrawing();
        Iterator i = drawing.sort(figures).iterator();
        while (i.hasNext()) {
            Figure figure = (Figure) i.next();
            drawing.bringToFront(figure);
        }
    }
     */
    
}
