package org.jhotdraw.draw;

import java.awt.Point;
import javax.swing.JViewport;

/**
 * Defines a decorated version of the DefaultDrawingView
 * that enables extension of the class with geometry related
 * functionality.
 *
 * Pattern: Decorator
 * Role: ConcreteDecorator
 * Associates: DefaultDrawingView (Component)
 *
 * @author jesper_wohlert
 */
public class GeometricDrawingView extends DefaultDrawingView {

    private DefaultDrawingView view;

    public GeometricDrawingView(DefaultDrawingView view) {
        this.view = view;
    }

    /**
     * Moves the focus of the drawing view to a
     * specific point.
     * @param point
     */
    public void moveView(Point point) {

        view.invalidate();

        if (view.getParent() != null) {
            view.getParent().validate();
            ((JViewport) view.getParent()).setViewPosition(point);
        }

        view.repaint();
    }

}
