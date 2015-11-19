/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;

/**
 * @author Jesper Madsen
 * @version 1.0.0
 */
public class Navigator extends JPanel {

    private Collection<Figure> figures;
    private DefaultDrawingView view;
    private double ScaleHeight, scaleWidth;
    private double scaleFactor;
    private final Double[] scaleFactors;
    private int scaleFactorsPointer;
    private Dimension prefferedDimension, drawingDimension;
    private int figureWidth = 0;
    private int figureHeight = 0;

    public Navigator(final DefaultDrawingView view) {
        this.scaleFactors = new Double[]{5.0, 4.0, 3.0, 2.0, 1.5, 1.25, 1.0, 0.75, 0.5, 0.25, 0.10};
        this.view = view;
        this.prefferedDimension = new Dimension(100, 100);
        this.drawingDimension = new Dimension(99, 99);
        this.setPreferredSize(prefferedDimension);
        this.figures = new ArrayList<Figure>();
        this.scaleFactor = view.getScaleFactor();
        this.scaleFactorsPointer = java.util.Arrays.asList(scaleFactors).indexOf((double) scaleFactor);

        //scaleFactor subscribtion on view
        view.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName() == DrawingView.SCALE_FACTOR_PROPERTY) {
                    if (evt.getNewValue() != null) {
                        scaleFactor = ((Double) evt.getNewValue());
                        repaint();
                    }
                }
            }
        });
        
//        //Not working
//        view.getDrawing().addFigureListener(new FigureAdapter() {
//            @Override
//            public void figureAdded(FigureEvent e){
//                System.out.println("herp");
//            }
//            @Override
//            public void figureRemoved(FigureEvent e){
//                System.out.println("derp");
//            }
//        });
        
        
        //MouseWheel listener
        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent mwe) {
                super.mouseWheelMoved(mwe);
                scaleFactorsPointer = java.util.Arrays.asList(scaleFactors).indexOf((double) view.getScaleFactor());
                if (scaleFactorsPointer == -1.0) {
                    scaleFactorsPointer = 6;
                }
                int notches = mwe.getWheelRotation();
                if (notches > 0) {
                    if (scaleFactorsPointer + 1 <= scaleFactors.length - 1) {
                        view.setScaleFactor(scaleFactors[++scaleFactorsPointer]);
                    } else {
                        scaleFactorsPointer = 0;
                        view.setScaleFactor(scaleFactors[scaleFactorsPointer]);
                    }
                } else {
                    if (scaleFactorsPointer - 1 >= 0) {
                        view.setScaleFactor(scaleFactors[--scaleFactorsPointer]);
                    } else {
                        scaleFactorsPointer = scaleFactors.length;
                        view.setScaleFactor(scaleFactors[--scaleFactorsPointer]);
                    }
                }
                getParent().repaint();
                revalidate();
                repaint();
            }
        });

        //Mouse listener
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                super.mousePressed(evt);
                if (scaleFactorsPointer - 1 >= 0) {
                    scaleFactor = scaleFactors[--scaleFactorsPointer];
                    final Rectangle vRect = view.getComponent().getVisibleRect();
                    double oldFactor = view.getScaleFactor();
                    view.setScaleFactor(scaleFactor);
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            if (vRect != null) {
                                //implement zoom relative to mouse coordinates (refactor to use in mousewheel)
                                //do math
                                view.getComponent().scrollRectToVisible(vRect);
                            }
                        }
                    });
                }
                getParent().repaint();
                revalidate();
                repaint();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        updateFigures(); //update and repaint on figurelist change, invoke later
        Rectangle2D clipBounds = new Rectangle(0, 0, figureWidth, figureHeight);
        if (clipBounds != null) {
            for (Figure f : figures) {
                if (f.isVisible() && f.getDrawingArea().intersects(clipBounds)) {
                    f.draw(g2);
                }
            }
        }
        g2.drawRect(0, 0, drawingDimension.width, drawingDimension.height);
    }

    private void updateFigures() {
        figures.removeAll(figures); //not unique collection
        for (Figure f : view.getDrawing().getChildren()) {
            figures.add((Figure) f.clone());
        }
        ScaleHeight = (double) drawingDimension.height / (figureHeight);
        AffineTransform transformer = new AffineTransform();
        transformer.scale(scaleWidth, ScaleHeight);
        //transform figure in relation to 'canvasSize' = figureWidth,figureHeight
        for (Figure f : figures) {
            if (f.getBounds().x + f.getBounds().width > figureWidth) {
                figureWidth = (int) (f.getBounds().x + f.getBounds().width);
                scaleWidth = (double) drawingDimension.width / (figureWidth);
            }
            if (f.getBounds().y + f.getBounds().height > figureHeight) {
                figureHeight = (int) (f.getBounds().y + f.getBounds().height);
                ScaleHeight = (double) drawingDimension.height / (figureHeight);
            }
            f.transform(transformer);
        }
    }
}
