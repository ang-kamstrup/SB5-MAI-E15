package org.jhotdraw.draw.action;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;
import org.jhotdraw.draw.DelegationSelectionTool;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.SelectionTool;
import org.jhotdraw.draw.ToolEvent;
import org.jhotdraw.draw.ToolListener;
import org.jhotdraw.draw.ZoomSelectionTool;

/**
 *
 * @author jesper_wohlert
 */
public class ZoomSelectionAction extends AbstractDrawingViewAction {

    private DrawingView view;
    private SelectionTool tool;
    
    public ZoomSelectionAction(DrawingView view) {
        super(view);
        this.view = view;
        tool = new ZoomSelectionTool();
    }
    
    public void actionPerformed(final ActionEvent e) {
        // Perform selection logic
        // Perform zoom
        // view.setScaleFactor(newValue)
        
        view.getEditor().setTool(tool);
        
        final ZoomSelectionAction self = this;
        
        ToolListener listener = new ToolListener() {
            
            public void toolStarted(ToolEvent event) {
                System.out.println(event.getInvalidatedArea());
            }

            public void toolDone(ToolEvent event) {
                System.out.println(event.getInvalidatedArea());
            }

            public void areaInvalidated(ToolEvent e) {
                // Unused for this action.           
            }
        };

        tool.addToolListener(listener);
    }
    
}
