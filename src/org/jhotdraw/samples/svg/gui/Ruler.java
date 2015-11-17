package org.jhotdraw.samples.svg.gui;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Fjessin
 */
public class Ruler extends JComponent {
    public static final int INCH = Toolkit.getDefaultToolkit().
        getScreenResolution();
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public static final int SIZE = 25;

    public int orientation;
    public boolean isMetric;
    private int increment;
    private int units;

    public Ruler(int o, boolean m) {
        orientation = o;
        isMetric = m;
        setIncrementAndUnits();
    }

    public void setIsMetric(boolean isMetric) {
        this.isMetric = isMetric;
        setIncrementAndUnits();
        repaint();
    }
 
    //trying to git...
    
    //more try.
    
    private void setIncrementAndUnits() {
        if (isMetric) {
            units = (int)((double)INCH / (double)2.54); // dots per centimeter
            increment = units;
        } else {
            units = INCH;
            increment = units / 2;
        }
    }

    public boolean isMetric() {
        return this.isMetric;
    }

    public int getIncrement() {
        return increment;
    }

    public void setPreferredHeight(int ph) {
        setPreferredSize(new Dimension(SIZE, ph));
    }

    public void setPreferredWidth(int pw) {
        setPreferredSize(new Dimension(pw, SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Rectangle drawHere = g.getClipBounds();

        //Fill clipping area with white.
        g.setColor(new Color(255, 255, 255));
        g.fillRect(drawHere.x, drawHere.y, drawHere.width, drawHere.height);

        //ruler labels in a small font that's black.
        g.setFont(new Font("SansSerif", Font.PLAIN, 10));
        g.setColor(Color.black);

        // Some vars we need for the ticks.
        int end = 0;
        int start = 0;
        int tickLength = 0;
        String text = null;

        // Using clipping bounds to calculate first and last tick locations.
        if (orientation == HORIZONTAL) {
            start = (drawHere.x / increment) * increment;
            end = (((drawHere.x + drawHere.width) / increment) + 1)
                  * increment;
        } else {
            start = (drawHere.y / increment) * increment;
            end = (((drawHere.y + drawHere.height) / increment) + 1)
                  * increment;
        }

        // Make a special case of 0 to display the number
        // within the ruler and draw units label. Next to the corner.
        if (start == 0) {
            text = Integer.toString(0) + (isMetric ? " cm" : " in");
            tickLength = 10;
            if (orientation == HORIZONTAL) {
                g.drawLine(0, SIZE-1, 0, SIZE-tickLength-1);
                g.drawString(text, 2, 12);
            } else {
                g.drawLine(SIZE-1, 0, SIZE-tickLength-1, 0);
                g.drawString(text, 1, 10);
            }
            text = null;
            start = increment;
        }

        // ticks and labels
        for (int i = start; i < end; i += increment) {
            //labels spacing for cm's
            if (i % units == 0)  {
                tickLength = 10;
                text = Integer.toString(i/units);
            }
            //inches spacing between labels
            else {
               tickLength = 7;
               text = null;
            }

            //drawing depending on the orientation
            //The ticks and labels depending on
            //wheter it's cm or inches
            if (tickLength != 0) {
                if (orientation == HORIZONTAL) {
                    g.drawLine(i, SIZE-1, i, SIZE-tickLength-1);
                    if (text != null)
                        g.drawString(text, i-3, 12);
                    
                } else {
                    g.drawLine(SIZE-1, i, SIZE-tickLength-1, i);
                    if (text != null)
                        g.drawString(text, 1, i+3);
                }
            } 
       
        }
    }
}
