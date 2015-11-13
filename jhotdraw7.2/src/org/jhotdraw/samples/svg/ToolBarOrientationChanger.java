package org.jhotdraw.samples.svg;

import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author admur13
 */
public class ToolBarOrientationChanger {
    private org.jhotdraw.samples.svg.gui.ToolsToolBar toolsPane;
    private SVGDrawingPanel dp = new SVGDrawingPanel();
    //public javax.swing.JPanel toolsPanel;
    private javax.swing.JPanel toolsPanel = new javax.swing.JPanel();

    public ToolBarOrientationChanger(JPanel toolsPanel) {
        this.toolsPanel = toolsPanel;
    }

    
    public void changeToVertical() {        
        JPanel parent = (JPanel)toolsPanel.getParent();
        parent.remove(toolsPanel);
        parent.revalidate();
        parent.repaint();
        parent.add(toolsPanel, BorderLayout.LINE_START);    
    }
    
    public void changeToHorizontal() {
        JComponent parent = (JComponent)toolsPanel.getParent();
        parent.remove(toolsPanel);
        parent.revalidate();
        parent.repaint();
        parent.add(toolsPanel, BorderLayout.SOUTH); 
    }
    
}
