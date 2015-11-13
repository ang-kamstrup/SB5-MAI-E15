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
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author admur13
 */
public class ChangeToHorizontalAction extends AbstractSelectedAction{

    private ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels", Locale.getDefault());
    private org.jhotdraw.samples.svg.gui.ToolsToolBar toolsPane;
    private javax.swing.JPanel toolsPanel;

    ChangeToHorizontalAction(DrawingEditor editor) {
        super(editor);
        labels.configureAction(this, "edit.changeToHorizontal");
        setEnabled(true);
    }
    
    
    public void actionPerformed(ActionEvent e) {
        System.out.println("Horizontal Button action performed!");
        changeToHorizontal();
        setEnabled(true);
    }
    
    public void changeToHorizontal(){
        // Code implementation here!
        JComponent parent = (JComponent)toolsPanel.getParent();
        parent.remove(toolsPanel);
        parent.revalidate();
        parent.repaint();
        parent.add(toolsPanel, BorderLayout.SOUTH); 
    }
    
    
    
}
