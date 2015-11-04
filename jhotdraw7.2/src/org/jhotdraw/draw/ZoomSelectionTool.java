package org.jhotdraw.draw;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;

/**
 *
 * @author jesper_wohlert
 */
public class ZoomSelectionTool extends SelectionTool {
    
    Tool tracker;
    int x, y, width, height;
    
    public ZoomSelectionTool() {
        super();
        this.tracker = this.getSelectAreaTracker();
    }
    
    @Override
    public void mouseClicked(MouseEvent evt) {
        this.x = evt.getXOnScreen();
        this.y = evt.getYOnScreen();
        if (getView() != null && getView().isEnabled()) {
            tracker.mouseClicked(evt);
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent evt) {
        
        height = Math.abs(this.y - evt.getYOnScreen());
        width = Math.abs(this.x - evt.getXOnScreen());
        
        if (getView() != null && getView().isEnabled()) {
            tracker.mouseReleased(evt);
            this.toolDone(new ToolEvent(this, this.getView(),
                    new Rectangle(x, y, height, width)));
        }
    }
    
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
