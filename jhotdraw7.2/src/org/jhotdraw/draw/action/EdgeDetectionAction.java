/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw.action;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.undo.*;
import org.jhotdraw.draw.*;
import org.jhotdraw.samples.svg.figures.SVGImageFigure;

/**
 *
 * @author klancar
 */
public class EdgeDetectionAction extends AbstractSelectedAction {
    
       public static String ID = "edit.edgeDetection";
       public static boolean changed = false;
   
    /** Creates a new instance. */
    public EdgeDetectionAction(DrawingEditor editor) {
        super(editor);
        labels.configureAction(this, ID);
    }

    public void actionPerformed(java.awt.event.ActionEvent e) {
        final DrawingView view = getView();
        final LinkedList<Figure> figures = new LinkedList<Figure>(view.getSelectedFigures());
        edgeDetection(figures);
        //boolean undoableEdit = getUndoableEdit(figures);
        //System.out.println(undoableEdit + " " + changed);
        if(/*undoableEdit && */ changed) {
            fireUndoableEditHappened(new AbstractUndoableEdit() {
                @Override
                public String getPresentationName() {
                    return labels.getTextProperty(ID);
                }
                @Override
                public void redo() throws CannotRedoException {
                    super.redo();
                    edgeDetection(figures);
                }
                @Override
                public void undo() throws CannotUndoException {
                    super.undo();
                    doUndo(figures);
                    
                }

            }

            );
        }
        
    }
    public void doUndo(LinkedList<Figure> figures) {
        Iterator<Figure> figuresIte = figures.iterator();
        while(figuresIte.hasNext()) {
             try {
                SVGImageFigure figure = (SVGImageFigure) figuresIte.next();
                figure.setBufferedImage(figure.getOriginalBufferedImage());
                figure.setEdgeDetectionApplied(false);
            } catch (Exception e) {

            }
        }
    }
    
    public static void edgeDetection(Collection<Figure> figures) {
        Iterator<Figure> figuresIte = figures.iterator();
        while(figuresIte.hasNext()) {
            try {
                SVGImageFigure figure = (SVGImageFigure) figuresIte.next();
                if(figure.getEdgeDetectionApplied() == true) {
                    JOptionPane.showMessageDialog(null, "Edge detection can be apllied only once per image",
                        "InfoBox: Edge detection" , JOptionPane.INFORMATION_MESSAGE);
                    changed = false;
                    return;
                }
                figure.setOriginalBufferedImage();
                BufferedImage img = figure.getBufferedImage();

                float[] kernel = getKernel();
                BufferedImageOp imgOp = new ConvolveOp(new Kernel(3, 3, kernel));
                img = imgOp.filter(img, null);
                img = correctImage(img);
                figure.setBufferedImage(img);
                figure.setEdgeDetectionApplied(true);
                changed = true;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Edge detection cannot be used on this figure, "
                        + "please select image figure and use edge detection tool.",
                        "InfoBox: Edge detection" , JOptionPane.INFORMATION_MESSAGE);
                changed = false;
            }
        }
        
    }
    private static BufferedImage correctImage(BufferedImage img) {
        BufferedImage newImg = img;
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++)
            {
                int rgb = img.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = (rgb & 0xFF);

                int corrected = (Integer.parseInt("FF", 16) << 24) + (r << 16) + (g << 8) + b; 
                newImg.setRGB(x, y, corrected);
            }
        }
        return newImg;
    }
    
    private static float[] getKernel() {
        float[] kernel = { 0.0f, -1.0f, 0.0f,
                            -1.0f, 4.0f, -1.0f,
                            0.0f, -1.0f, 0.0f};
        return kernel;
    }

    private boolean getUndoableEdit(LinkedList<Figure> figures) {
        boolean undoableEdit = false;
        
        Iterator<Figure> figuresIte = figures.iterator();
        while(figuresIte.hasNext()) {
            try {
                SVGImageFigure figure = (SVGImageFigure) figuresIte.next();
                if(figure.getEdgeDetectionApplied() == true) {
                    undoableEdit = true;
                    break;
                }
            } catch (Exception exp) {

            }
        }
        return undoableEdit;
    }
}