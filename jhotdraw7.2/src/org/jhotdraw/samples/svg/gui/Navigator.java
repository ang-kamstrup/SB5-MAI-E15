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
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JPanel;
import org.jhotdraw.draw.DefaultDrawing;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.action.AlignAction;

/**
 * @author Jesper Madsen
 * @version 0.9.0
 * 
 * Have no implementation in regard to "current" drawing area and zoom
 * needs updating when that gets working :
 *      #3  Feature request: Add smooth zoom function (scroll based) 
 *      #50 Resizable Canvas 
 * Created a MouseListenter for when this gets relevant
 * requestFocus() not working, following mouseWheelListener does not either.
 */
public class Navigator extends JPanel {

    private Collection<Figure> figures;
    private DefaultDrawingView view;
    private double height, width;

    public Navigator(DefaultDrawingView view) {
        this.setPreferredSize(new Dimension(100, 100));
        this.view = view;
        height = 100.0 / view.getHeight();
        width = 100.0 / view.getWidth();
        figures = new ArrayList<Figure>();
        this.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("setCurrentViewingArea{new Rect(....)}");
            }
            
            @Override
            public void mouseEntered(MouseEvent e){
//                requestFocus(); 
//                requestFocusInWindow();
            }
            
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) { 
                int ticks = e.getWheelRotation();
                if (ticks < 0) {
                    System.out.println("Zoom in");
                } else {
                    System.out.println("Zoom out");
                }
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        updateFigures(); //rethink? perhaps thread
        Rectangle2D clipBounds = new Rectangle(0, 0, view.getWidth(), view.getHeight());
        if (clipBounds != null) {
            for (Figure f : figures) {
                if (f.isVisible() && f.getDrawingArea().intersects(clipBounds)) {
                    f.draw(g2);
                }
            }
        }
        g2.drawRect(0, 0, 99, 99);
    }

    private void updateFigures() {
        figures.removeAll(figures); //not unique collection
        height = 100.0 / (view.getHeight()); //+view.getHeight+zoomFactor
        width = 100.0 / (view.getWidth()); //+view.getWidth+zoomFactor
        for (Figure f : view.getDrawing().getChildren()) {
            figures.add((Figure) f.clone()); 
        }
        AffineTransform transformer = new AffineTransform();
        transformer.scale(width, height);
        for (Figure f : figures) {
            f.transform(transformer);
        }
    }
}
