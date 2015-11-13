package org.jhotdraw.draw.action;

import java.awt.event.ActionEvent;
import java.util.Locale;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.samples.svg.ToolBarOrientationChanger;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author admur13
 */
public class ChangeToVerticalAction extends AbstractSelectedAction {
    private ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels", Locale.getDefault());
    private org.jhotdraw.samples.svg.gui.ToolsToolBar toolsPane;
    private javax.swing.JPanel toolsPanel;
    
    public ToolBarOrientationChanger oriChanger = new ToolBarOrientationChanger(toolsPanel);
    
    public ChangeToVerticalAction(DrawingEditor editor) {
        super(editor);
        labels.configureAction(this, "edit.changeToVertical");
        setEnabled(true);
    }
    public void actionPerformed(ActionEvent e) {
        System.out.println("Vertical Button action performed!");
        //changeToVertical();
        oriChanger.changeToVertical();
        System.out.println("Did it work?");
        setEnabled(true);
    }
    
    public void changeToVertical() {
 
        
    }
    
}
