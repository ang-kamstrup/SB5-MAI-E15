package org.jhotdraw.draw.action;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import javax.swing.JPanel;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.samples.svg.SVGDrawingPanel;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author Tecor13
 */
class Graph extends AbstractSelectedAction {
    private ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels", Locale.getDefault());
    private JPanel toolsPanel;
    private JPanel toolsPane;
    private SVGDrawingPanel svgDrawingPanel;
    
    public Graph(DrawingEditor editor) {
        super(editor);
        labels.configureAction(this, "edit.insertGraph");
        setEnabled(true);
    }

    public Graph(DrawingEditor editor, SVGDrawingPanel svgDrawingPanel, JPanel toolsPane) {
        super(editor);
        labels.configureAction(this, "edit.insertGraph");
        setEnabled(true);
        this.svgDrawingPanel = svgDrawingPanel;
        this.toolsPane = toolsPane;
        
    }
    
    public void actionPerformed(ActionEvent e){
        System.out.println("Done done done...?");
    }
    
    public void setSVGDrawingPanel(SVGDrawingPanel svgDrawingPanel){
        this.svgDrawingPanel = svgDrawingPanel;
    }
}
