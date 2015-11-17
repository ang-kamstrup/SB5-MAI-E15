/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw.action;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.Locale;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.gui.ToolBarLayout;
import org.jhotdraw.samples.svg.SVGDrawingPanel;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author admur13
 */
public class ChangeToHorizontalAction extends AbstractSelectedAction {

    private ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels", Locale.getDefault());
    private JPanel toolsPanel;
    private JPanel toolsPane;
    private SVGDrawingPanel svgDrawingPanel;

    ChangeToHorizontalAction(DrawingEditor editor) {
        super(editor);
        labels.configureAction(this, "edit.changeToHorizontal");
        setEnabled(true);
    }
    
        public ChangeToHorizontalAction(DrawingEditor editor, SVGDrawingPanel svgDrawingPanel, JPanel toolsPane) {
        super(editor);
        labels.configureAction(this, "edit.changeToVertical");
        setEnabled(true);
        this.svgDrawingPanel = svgDrawingPanel;
        this.toolsPane = toolsPane;
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Horizontal Button action performed!");
        changeToHorizontal();
        setEnabled(true);
    }
    
    public void setSVGDrawingPanel(SVGDrawingPanel svgDrawingPanel) {
        this.svgDrawingPanel = svgDrawingPanel;
    }

    public void changeToHorizontal() {
        this.toolsPanel = svgDrawingPanel.getToolsPanel();
         
        JPanel parent = (JPanel)toolsPanel.getParent();
        parent.remove(toolsPanel);
        
        toolsPane.setLayout(new ToolBarLayout());
        parent.revalidate();
        parent.repaint();
        parent.add(toolsPanel, BorderLayout.SOUTH);
    }
}
