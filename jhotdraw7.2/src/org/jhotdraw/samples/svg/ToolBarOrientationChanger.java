package org.jhotdraw.samples.svg;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.jhotdraw.gui.ToolBarLayout;
import org.jhotdraw.samples.svg.gui.Navigator;

/**
 *
 * @author admur13
 */
public class ToolBarOrientationChanger {
    private org.jhotdraw.samples.svg.gui.ToolsToolBar toolsPane;
    private SVGDrawingPanel dp = new SVGDrawingPanel();
    private javax.swing.JPanel toolsPanel;

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
