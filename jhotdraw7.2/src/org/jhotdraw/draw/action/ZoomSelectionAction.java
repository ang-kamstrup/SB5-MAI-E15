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
 * Action called for zooming to a selection.
 * uses the ZoomSelectionTool to perform selection.
 * @author jesper_wohlert
 */
public class ZoomSelectionAction extends AbstractDrawingViewAction {

    private DrawingView view;
    private SelectionTool selectionTool;
    boolean isZoomed = false;
    
    /**
     * Initialises the action with a view.
     * @param view 
     */
    public ZoomSelectionAction(DrawingView view) {
        super(view);
        this.view = view;
        selectionTool = new ZoomSelectionTool();
    }
    
    /**
     * Called when the action is performed, e.g. by clicking a button.
     * Usually works as a callback with the event.
     * @param _ ActionEvent received from caller.
     */
    @Override
    public void actionPerformed(final ActionEvent _) {
        
        if (isZoomed) {
            view.setScaleFactor(1d);
            isZoomed = false;
            return;
        }
        
        view.getEditor().setTool(selectionTool);
        
        //final ZoomSelectionAction self = this;
        
        ToolListener listener = new ToolListener() {
            
            public void toolStarted(ToolEvent event) {
                //System.out.println(event.getInvalidatedArea());
            }

            public void toolDone(ToolEvent event) {
                int w = event.getInvalidatedArea().width;
                view.setScaleFactor(1d);
                isZoomed = true;
            }

            public void areaInvalidated(ToolEvent event) {
                // Unused for this action.           
            }
        };

        selectionTool.addToolListener(listener);
    }
    
}
