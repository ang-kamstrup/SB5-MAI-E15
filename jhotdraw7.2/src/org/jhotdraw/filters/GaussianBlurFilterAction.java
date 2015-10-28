package org.jhotdraw.filters;

import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.AbstractAction;
import org.jhotdraw.app.Application;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.action.AbstractSelectedAction;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author Dan/Kyzaq
 */
public class GaussianBlurFilterAction extends AbstractAction {
    public final static String ID = "filter.gaussianBlur";

    /*public GaussianBlurFilterAction(DrawingEditor editor) {
        super(editor);
    }*/
    
    public GaussianBlurFilterAction(Application app){
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.labels");
        labels.configureAction(this, ID);
    }
    
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /*
     * final DrawingView view = getView();
        final LinkedList<Figure> figures = new LinkedList<Figure>(view.getSelectedFigures());
    public static void bringToFront(DrawingView view, Collection<Figure> figures) {
        Drawing drawing = view.getDrawing();
        Iterator i = drawing.sort(figures).iterator();
        while (i.hasNext()) {
            Figure figure = (Figure) i.next();
            drawing.bringToFront(figure);
        }
    }
     */
    
}
