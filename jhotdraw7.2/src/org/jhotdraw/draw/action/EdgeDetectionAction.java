/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw.action;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.*;
import javax.swing.undo.*;
import org.jhotdraw.draw.*;
import org.jhotdraw.samples.svg.figures.SVGImageFigure;

/**
 *
 * @author klancar
 */
public class EdgeDetectionAction extends AbstractSelectedAction {
    
       public static String ID = "edit.edgeDetection";

       
    /** Creates a new instance. */
    public EdgeDetectionAction(DrawingEditor editor) {
        super(editor);
        labels.configureAction(this, ID);
    }

    public void actionPerformed(java.awt.event.ActionEvent e) {
        final DrawingView view = getView();
        final LinkedList<Figure> figures = new LinkedList<Figure>(view.getSelectedFigures());
        edgeDetection(view, figures);
        fireUndoableEditHappened(new AbstractUndoableEdit() {
            @Override
            public String getPresentationName() {
       return labels.getTextProperty(ID);
            }
            @Override
            public void redo() throws CannotRedoException {
                super.redo();
                //BringToFrontAction.bringToFront(view, figures);
            }
            @Override
            public void undo() throws CannotUndoException {
                super.undo();
                //SendToBackAction.sendToBack(view, figures);
            }
        }
        
        );
    }
    public static void edgeDetection(DrawingView view, Collection<Figure> figures) {
        Iterator<Figure> figuresIte = figures.iterator();
        while(figuresIte.hasNext()) {
            try {
                SVGImageFigure figure = (SVGImageFigure) figuresIte.next();
                BufferedImage img = figure.getBufferedImage();
                /*float[] kernel = { -1.0f, -1.0f, -1.0f,
                                        -1.0f, 8.0f, -1.0f,
                                        -1.0f, -1.0f, -1.0f};*/
                float[] kernel = { 0.0f, -1.0f, 0.0f,
                                        -1.0f, 4.0f, -1.0f,
                                        0.0f, -1.0f, 0.0f};
                /*float[] kernel = {0, 0, -1, -1, -1, 0, 0, 
                                    0, -2, -3, -3, -3, -2, 0, 
                                    -1, -3, 5, 5, 5, -3, -1, 
                                    -1, -3, 5, 16, 5, -3, -1,
                                    -1, -3, 5, 5, 5, -3, -1, 
                                    0, -2, -3, -3, -3, -2, 0,
                                    0, 0, -1, -1, -1, 0, 0};*/
                BufferedImageOp imgOp = new ConvolveOp(new Kernel(3, 3, kernel));

                img = imgOp.filter(img, null);
                for (int x = 0; x < img.getWidth(); x++) {
                    for (int y = 0; y < img.getHeight(); y++)
                    {
                        int rgb = img.getRGB(x, y);
                        int r = (rgb >> 16) & 0xFF;
                        int g = (rgb >> 8) & 0xFF;
                        int b = (rgb & 0xFF);

                        int corrected = (Integer.parseInt("FF", 16) << 24) + (r << 16) + (g << 8) + b; 
                        img.setRGB(x, y, corrected);
                    }
                }

                figure.setBufferedImage(img);
            } catch (Exception e) {
                System.out.println("Image not selected.");
                //System.out.println(e);
            }
        }
        
    }
}