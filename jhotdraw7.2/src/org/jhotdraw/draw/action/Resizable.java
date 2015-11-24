/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw.action;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.DrawingView;
import static org.jhotdraw.draw.AttributeKeys.*;
import org.jhotdraw.draw.DefaultDrawing;

/**
 *
 * @author Nevethan
 */
public class Resizable extends JComponent{
    
    public Resizable(Component c){
        this(c,new ResizableBorder(8));
    }
    
    public Resizable(Component c, ResizableBorder border){
        setLayout(new BorderLayout());
        add(c);
        addMouseListener(resizeListener);
        addMouseMotionListener(resizeListener);
        setBorder(border);
    } 
    //Hvordan skal Canvas'en resizes???
    private void resize(){
        if((JComponent)getParent() != null){getParent().validate();}
    }
    
    MouseInputListener resizeListener = new MouseInputAdapter() {
        
        @Override
        public void mouseMoved(MouseEvent me) {
            if (hasFocus()) {
                ResizableBorder border = (ResizableBorder) getBorder();
                setCursor(Cursor.getPredefinedCursor(border.getCursor(me)));
            }
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
            setCursor(Cursor.getDefaultCursor());
        }

        private int cursor;
        private Point startPos = null;

        @Override
        public void mousePressed(MouseEvent me) {
            
            ResizableBorder border = (ResizableBorder) getBorder();
            cursor = border.getCursor(me);
            startPos = me.getPoint();
            requestFocus();
            repaint();
        }
        private DefaultDrawing view;
        //private DrawingView view2;

        @Override
        public void mouseDragged(MouseEvent me) {

            if (startPos != null) {
                int x = getX(); 
                int y = getY();
                
                int w = getWidth();
                int h = getHeight();

                int dx = me.getX() - startPos.x;
                int dy = me.getY() - startPos.y;

                
                switch (cursor) {
                    case Cursor.N_RESIZE_CURSOR:
                        if (!(h - dy < 50)) {
                            setBounds(x, y + dy, w, h - dy);
                            System.out.println(w);
                            view.getDrawing().setAttribute(CANVAS_HEIGHT, -50.00);
                            resize();
                        }
                        break;

                    case Cursor.S_RESIZE_CURSOR:
                        if (!(h + dy < 50)) {
                            setBounds(x, y, w, h + dy);
                            System.out.println(h);
                            startPos = me.getPoint();
                            view.getDrawing().setAttribute(CANVAS_HEIGHT, 50.00);
                            resize();
                        }
                        break;

                    case Cursor.W_RESIZE_CURSOR:
                        if (!(w - dx < 50)) {
                            setBounds(x + dx, y, w - dx, h);
                            view.getDrawing().setAttribute(CANVAS_WIDTH, 50.00);
                            resize();
                        }
                        break;

                    case Cursor.E_RESIZE_CURSOR:
                        if (!(w + dx < 50)) {
                            setBounds(x, y, w + dx, h);
                            startPos = me.getPoint();
                            view.getDrawing().setAttribute(CANVAS_WIDTH, 50.00);
                            resize();
                        }
                        break;

                    case Cursor.NW_RESIZE_CURSOR:
                        if (!(w - dx < 50) && !(h - dy < 50)) {
                            setBounds(x + dx, y + dy, w - dx, h - dy);
                            view.getDrawing().setAttribute(CANVAS_HEIGHT, -25.00);
                            view.getDrawing().setAttribute(CANVAS_WIDTH, -25.00);
                            resize();
                        }
                        break;

                    case Cursor.NE_RESIZE_CURSOR:
                        if (!(w + dx < 50) && !(h - dy < 50)) {
                            setBounds(x, y + dy, w + dx, h - dy);
                            startPos = new Point(me.getX(), startPos.y);
                            view.getDrawing().setAttribute(CANVAS_HEIGHT, -25.00);
                            view.getDrawing().setAttribute(CANVAS_WIDTH, -25.00);
                            resize();
                        }
                        break;

                    case Cursor.SW_RESIZE_CURSOR:
                        if (!(w - dx < 50) && !(h + dy < 50)) {
                            setBounds(x + dx, y, w - dx, h + dy);
                            startPos = new Point(startPos.x, me.getY());
                            view.getDrawing().setAttribute(CANVAS_HEIGHT, 25.00);
                            view.getDrawing().setAttribute(CANVAS_WIDTH, 25.00);
                            resize();
                        }
                        break;

                    case Cursor.SE_RESIZE_CURSOR:
                        if (!(w + dx < 50) && !(h + dy < 50)) {
                            setBounds(x, y, w + dx, h + dy);
                            startPos = me.getPoint();
                            view.getDrawing().setAttribute(CANVAS_HEIGHT, 25.00);
                            view.getDrawing().setAttribute(CANVAS_WIDTH, 25.00);
                            resize();
                        }
                        break;

                    case Cursor.MOVE_CURSOR:
                        Rectangle bounds = getBounds();
                        bounds.translate(dx, dy);
                        setBounds(bounds);
                        resize();
                }

                setCursor(Cursor.getPredefinedCursor(cursor));
            }
        }

        /*@Override
        public void mouseReleased(MouseEvent mouseEvent) {
            startPos = null;
        }*/ 
    };
}
