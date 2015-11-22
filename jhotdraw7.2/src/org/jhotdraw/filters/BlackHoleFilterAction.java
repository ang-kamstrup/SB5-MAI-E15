/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.filters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
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
 * @author Martin
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
        createFilter();
        originalFigure.setBufferedImage(blackHoleImage);
    }

    private void createFilter() {
        blackHoleImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), originalImage.getType());

        Color color = Color.BLACK;
        int blackPixel = color.getRGB();

        for (int y = 0; y < originalImage.getHeight(); y++) {
            for (int x = 0; x < originalImage.getWidth(); x++) {

                if (pixelNumber(x,y,0.35f,0.65f)) {
                    blackHoleImage.setRGB(x, y, blackPixel);

                } else if(pixelNumber(x,y,0.30f,0.70f)){
                    color = new Color(originalImage.getRGB(x, y));
                    color = convertColor(color, 25);
                    blackHoleImage.setRGB(x, y, color.getRGB());
                }else if (pixelNumber(x,y,0.25f,0.75f)) {
                    color = new Color(originalImage.getRGB(x, y));
                    color = convertColor(color, 50);
                    blackHoleImage.setRGB(x, y, color.getRGB());
                } else if(pixelNumber(x,y,0.20f,0.80f)){
                    color = new Color(originalImage.getRGB(x, y));
                    color = convertColor(color, 75);
                    blackHoleImage.setRGB(x, y, color.getRGB());
                }else if (pixelNumber(x,y,0.15f,0.85f)) {
                    color = new Color(originalImage.getRGB(x, y));
                    color = convertColor(color, 100);
                    blackHoleImage.setRGB(x, y, color.getRGB());
                } else if(pixelNumber(x,y,0.10f,0.90f)){
                    color = new Color(originalImage.getRGB(x, y));
                    color = convertColor(color, 125);
                    blackHoleImage.setRGB(x, y, color.getRGB());
                }else if(pixelNumber(x,y,0.05f,0.95f)){
                    color = new Color(originalImage.getRGB(x, y));
                    color = convertColor(color, 150);
                    blackHoleImage.setRGB(x, y, color.getRGB());
                }else{
                    blackHoleImage.setRGB(x, y, originalImage.getRGB(x, y));
                }

            }
        }

    }

    private Color convertColor(Color color, int increase) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        int newRed = 0;
        int newGreen = 0;
        int newBlue = 0;

        newRed += increase;
        if (newRed > red) {
            newRed = red;
        }

        newGreen += increase;
        if (newGreen > green) {
            newGreen = green;
        }

        newBlue += increase;
        if (newBlue > blue) {
            newBlue = blue;
        }


        Color newColor = new Color(newRed, newGreen, newBlue);
        return newColor;
    }
    
    private boolean pixelNumber(int x, int y, float percentageLow, float percentageHigh){
        if(x > originalImage.getWidth() * percentageLow && x < originalImage.getWidth() * percentageHigh
                        && y > originalImage.getHeight() * percentageLow && y < originalImage.getHeight() * percentageHigh){
            return true;
        }
        return false;
    }
}
