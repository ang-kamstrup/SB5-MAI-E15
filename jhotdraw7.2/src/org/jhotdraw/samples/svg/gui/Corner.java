package org.jhotdraw.samples.svg.gui;

import java.awt.*;
import javax.swing.*;
/**
 *
 * @author Fjessin
 */

/* Corner.java is used by for creating the corner for the ruler. */

public class Corner extends JComponent {
    protected void paintComponent(Graphics g) {
        
        g.setColor(new Color(255, 255, 255));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}