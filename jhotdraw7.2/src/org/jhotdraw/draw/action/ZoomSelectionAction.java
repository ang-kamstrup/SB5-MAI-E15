package org.jhotdraw.draw.action;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
    private ToolListener listener;
    private SelectionTool selectionTool;
    private boolean isZoomed = false;
    
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
     * @param _ ActionEvent received from caller (unused).
     */
    @Override
    public void actionPerformed(final ActionEvent _) {
                
        if (isZoomed) {
            view.setScaleFactor(1d);
            isZoomed = false;
            for (PropertyChangeListener changeListener : this.getPropertyChangeListeners()) {
                changeListener.propertyChange(new PropertyChangeEvent(this, 
                        "SELECTION_ENDED", true, true));
            }
            selectionTool.removeToolListener(listener);
            return;
        }
        
        view.getEditor().setTool(selectionTool);
        
        final ZoomSelectionAction self = this;
        
        listener = new ToolListener() {
            
            public void toolStarted(ToolEvent event) {
                // Unused for this action.
            }

            public void toolDone(ToolEvent event) {
                int w = event.getInvalidatedArea().width;
                int h = event.getInvalidatedArea().height;
                
                double range = 0;
                
                if (w >= h) {
                    range = Toolkit.getDefaultToolkit().getScreenSize().width / w;
                } else {
                    range = Toolkit.getDefaultToolkit().getScreenSize().height / h;
                }

                view.setScaleFactor(range);
                for (PropertyChangeListener changeListener : self.getPropertyChangeListeners()) {
                    changeListener.propertyChange(new PropertyChangeEvent(self, 
                            "SELECTION_ENDED", true, true));
                }
                isZoomed = true;
                selectionTool.removeToolListener(this);
            }

            public void areaInvalidated(ToolEvent event) {
                // Unused for this action.           
            }
        };

        selectionTool.addToolListener(listener);
    }
    
}
