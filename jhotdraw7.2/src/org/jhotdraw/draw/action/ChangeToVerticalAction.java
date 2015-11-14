package org.jhotdraw.draw.action;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.Locale;
import javax.swing.JPanel;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.gui.ToolBarLayout;
import org.jhotdraw.samples.svg.SVGDrawingPanel;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author admur13
 */
public class ChangeToVerticalAction extends AbstractSelectedAction {
    private ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels", Locale.getDefault()); 
    private JPanel toolsPanel;
    private JPanel toolsPane;
    private SVGDrawingPanel svgDrawingPanel;

    public ChangeToVerticalAction(DrawingEditor editor) {
        super(editor);
        labels.configureAction(this, "edit.changeToVertical");
        setEnabled(true);
    }
    
    public ChangeToVerticalAction(DrawingEditor editor, SVGDrawingPanel svgDrawingPanel, JPanel toolsPane) {
        super(editor);
        labels.configureAction(this, "edit.changeToVertical");
        setEnabled(true);
        this.svgDrawingPanel = svgDrawingPanel;
        this.toolsPane = toolsPane;
    }
    
    public void actionPerformed(ActionEvent e) {
        System.out.println("Vertical Button action performed!");
        changeToVertical();
        System.out.println("Did it work?");
        setEnabled(true);
    }
    
    public void setSVGDrawingPanel(SVGDrawingPanel svgDrawingPanel) {
        this.svgDrawingPanel = svgDrawingPanel;
    }
    
    public void changeToVertical() {
        
        this.toolsPanel = svgDrawingPanel.getToolsPanel();
        
        //JPanel jPane = (SVGDrawingPanel) toolsPanel.getV
        //svgDrawingPanel.toVertical();
        
        JPanel parent = (JPanel)toolsPanel.getParent();
        parent.remove(toolsPanel);
        
        toolsPane.setLayout(new ToolBarLayout(1));
        parent.revalidate();
        parent.repaint();
        parent.add(toolsPanel, BorderLayout.LINE_START);
        
        
    }
    
}
