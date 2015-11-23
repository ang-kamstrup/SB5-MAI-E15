package org.jhotdraw.draw.action;


import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.DrawingView;
import static org.jhotdraw.draw.AttributeKeys.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nevethan
 */
public class ResizableBorder implements Border {
    
    private int distance = 8;
    
    int locs[] = {SwingConstants.NORTH, SwingConstants.SOUTH, SwingConstants.WEST,
        SwingConstants.EAST, SwingConstants.NORTH_WEST,
        SwingConstants.NORTH_EAST, SwingConstants.SOUTH_WEST,
        SwingConstants.SOUTH_EAST
    };
    
    int cursors[] = {Cursor.N_RESIZE_CURSOR, Cursor.S_RESIZE_CURSOR, Cursor.W_RESIZE_CURSOR,
        Cursor.E_RESIZE_CURSOR, Cursor.NW_RESIZE_CURSOR, Cursor.NE_RESIZE_CURSOR,
        Cursor.SW_RESIZE_CURSOR, Cursor.SE_RESIZE_CURSOR
    };
    private Rectangle r;
    
    
    public ResizableBorder(int distance){
        this.distance = distance;
    }
    
    public Insets getBorderInsets(Component c){
        return new Insets(distance, distance, distance, distance);
    }
    
    public boolean isBorderOpaque(){ return false;}

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        
        g.setColor(Color.BLACK);
        g.drawRect(x+distance /2, y+distance / 2, width-distance, height-distance);
        
        if(c.hasFocus()){
            for (int i = 0; i < locs.length; i++) {
                Rectangle rect = getRectangle(x, y, width, height, locs[i]);
                g.setColor(Color.WHITE);
                g.fillRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
                g.setColor(Color.BLACK);
                g.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
            }
        
        }
    }
        

    private Rectangle getRectangle(int x, int y, int w, int h, int location) {

        switch (location) {
            case SwingConstants.NORTH:
                return new Rectangle(x + w / 2 - distance / 2, y, distance, distance);
            case SwingConstants.SOUTH:
                return new Rectangle(x + w / 2 - distance / 2, y + h - distance, distance,
                        distance);
            case SwingConstants.WEST:
                return new Rectangle(x, y + h / 2 - distance / 2, distance, distance);
            case SwingConstants.EAST:
                return new Rectangle(x + w - distance, y + h / 2 - distance / 2, distance,
                        distance);
            case SwingConstants.NORTH_WEST:
                return new Rectangle(x, y, distance, distance);
            case SwingConstants.NORTH_EAST:
                return new Rectangle(x + w - distance, y, distance, distance);
            case SwingConstants.SOUTH_WEST:
                return new Rectangle(x, y + h - distance, distance, distance);
            case SwingConstants.SOUTH_EAST:
                return new Rectangle(x + w - distance, y + h - distance, distance, distance);
        }
        return null;
    }

    public int getCursor(MouseEvent me) {

        Component c = me.getComponent();
        int w = c.getWidth();
        int h = c.getHeight();

        for (int i = 0; i < locs.length; i++) {
            Rectangle rect = getRectangle(0, 0, w, h, locs[i]);
            if (rect.contains(me.getPoint())) {
                return cursors[i];
            }
        }

        return Cursor.MOVE_CURSOR;
    }
}
