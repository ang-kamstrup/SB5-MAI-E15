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
     * Initializes the tool with an area tracker.
     */
    public ZoomSelectionTool() {
        super();
        this.tracker = this.getSelectAreaTracker();
    }
    
    @Override
    public void mousePressed(MouseEvent evt) {
        if (getView() != null && getView().isEnabled()) {
            tracker.mousePressed(evt);
            this.toolStarted(new ToolEvent(this, this.getView(),
                    new Rectangle(evt.getXOnScreen(), evt.getYOnScreen())));
        }
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
        resetTracker();
        
        Object[] listeners = listenerList.getListenerList();
        
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i] == ToolListener.class) {
                ((ToolListener)listeners[i+1]).toolDone(event);
            }
        }
    }
    
    /**
     * Fires when selection starts.
     * @param event 
     */
    @Override
    public void toolStarted(ToolEvent event) {
        resetTracker();
        
        Object[] listeners = listenerList.getListenerList();
        
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i] == ToolListener.class) {
                ((ToolListener)listeners[i+1]).toolStarted(event);
            }
        }
    }
    
    /**
     * Deactivates the current tracker and replaces all its listeners.
     */
    private void resetTracker() {
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
    }
    
}
