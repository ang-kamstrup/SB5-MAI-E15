package org.jhotdraw.draw;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;

/**
 * Tool for zooming to a selected area.
 * @author jesper_wohlert
 */
public class ZoomSelectionTool extends SelectionTool {
    
    Tool tracker;
    
    /**
     * Initialises the tool with an area tracker.
     */
    public ZoomSelectionTool() {
        super();
        this.tracker = this.getSelectAreaTracker();
    }
    
    @Override
    public void mouseReleased(MouseEvent evt) {
        
        if (getView() != null && getView().isEnabled()) {
            tracker.mouseReleased(evt);
            this.toolDone(new ToolEvent(this, this.getView(),
                    new Rectangle(evt.getXOnScreen(), evt.getYOnScreen())));
        }
    }
    
    /**
     * Fired when the selection has ended.
     * Sends the invalidated area (rectangle) back to the caller.
     * @param event 
     */
    @Override
    public void toolDone(ToolEvent event) {
        // Empty
        Tool newTracker = getSelectAreaTracker();

        if (newTracker != null) {
            if (tracker != null) {
                tracker.deactivate(getEditor());
                tracker.removeToolListener(this);
            }
            tracker = newTracker;
            tracker.activate(getEditor());
            tracker.addToolListener(this);
        }
        
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i] == ToolListener.class) {
                ((ToolListener)listeners[i+1]).toolDone(event);
            }
        }
    }
    
}
