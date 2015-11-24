package org.jhotdraw.draw.action;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.jhotdraw.app.JHotDrawFeatures;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.GeometricDrawingView;
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

    private DefaultDrawingView view;
    private GeometricDrawingView geoView;

    private ToolListener listener;
    private SelectionTool selectionTool;
    private boolean isZoomed = false;

    /**
     * Initializes the action with a view to act upon.
     * @param view
     */
    public ZoomSelectionAction(DrawingView view) {
        super(view);
        this.view = (DefaultDrawingView) view;
        this.geoView = new GeometricDrawingView(this.view);
        selectionTool = new ZoomSelectionTool();
    }

    /**
     * Performs the zoom and move of the view in response
     * to some zoom-able area.
     * @param area to be zoomed in on.
     */
    private void performAction(Rectangle area) {
        int width = view.getWidth();

        if (area.width != 0) {
            double factor = width / area.width;
            view.setScaleFactor(factor);

            geoView.moveView(new Point(
                (int) (((area.width - area.x)/2)*factor),
                (int) (((area.height - area.y)/2)*factor)));
        }
    }
    
    /**
     * Called when the action is performed, e.g. by clicking a button.
     * Usually works as a callback with the event.
     * @param _ ActionEvent received from caller (unused).
     */
    @FeatureEntryPoint(JHotDrawFeatures.ZOOM_SELECTION)
    @Override
    public void actionPerformed(final ActionEvent _) {

        if (isZoomed) {
            view.setScaleFactor(1.0d);
            isZoomed = false;

            // Notify all listeners
            for (PropertyChangeListener changeListener : this.getPropertyChangeListeners()) {
                changeListener.propertyChange(new PropertyChangeEvent(this,
                        "SELECTION_ENDED", true, true));
            }

            selectionTool.removeToolListener(listener);

        } else {
            view.getEditor().setTool(selectionTool);

            // Internally scoped action
            final ZoomSelectionAction self = this;

            listener = new ToolListener() {
                int x = 0;
                int y = 0;

                @Override
                public void toolStarted(ToolEvent event) {
                    Rectangle area = event.getInvalidatedArea();
                    x = area.width;
                    y = area.height;
                }

                @Override
                public void toolDone(ToolEvent event) {
                    Rectangle area = event.getInvalidatedArea();
                    
                    Rectangle r = new Rectangle(x, y, 
                            Math.abs(area.width - x), Math.abs(area.height - y));

                    self.performAction(r);

                    // Notify all listeners
                    for (PropertyChangeListener changeListener : self.getPropertyChangeListeners()) {
                        changeListener.propertyChange(new PropertyChangeEvent(self,
                                "SELECTION_ENDED", true, true));
                    }

                    if (view.getScaleFactor() != 1.0) {
                        isZoomed = true;
                    }

                    selectionTool.removeToolListener(this);
                }

                @Override
                public void areaInvalidated(ToolEvent event) {
                    // Unused for this action
                }
            };

            selectionTool.addToolListener(listener);
        }
    }
}
