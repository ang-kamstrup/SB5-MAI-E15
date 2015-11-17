package org.jhotdraw.draw.action;

import java.awt.*;
import java.net.*;
import org.jhotdraw.draw.*;
import static org.jhotdraw.draw.AttributeKeys.FILL_COLOR;
import org.jhotdraw.samples.svg.Gradient;

/**
 * 
 * @author Peter G. Andersen - peand13@student.sdu.dk
 */
public class SelectionGradientColorIcon extends javax.swing.ImageIcon {
    private DrawingEditor editor;
    private AttributeKey<Gradient> key;
    private int stop;
    private Shape colorShape;
    
    /** Creates a new instance.
     * @param editor The drawing editor.
     * @param key The key of the default attribute
     * @param imageLocation the icon image
     * @param colorShape The shape to be drawn with the color of the default
     * attribute.
     */
    public SelectionGradientColorIcon(
            DrawingEditor editor,
            int stop,
            AttributeKey<Gradient> key,
            URL imageLocation,
            Shape colorShape) {
        super(imageLocation);
        this.editor = editor;
        this.stop = stop;
        this.key = key;
        this.colorShape = colorShape;
    }
    public SelectionGradientColorIcon(
            DrawingEditor editor,
            int stop,
            AttributeKey<Gradient> key,
            Image image,
            Shape colorShape) {
        super(image);
        this.editor = editor;
        this.stop = stop;
        this.key = key;
        this.colorShape = colorShape;
    }
    
    @Override
    public void paintIcon(java.awt.Component c, java.awt.Graphics gr, int x, int y) {
        Graphics2D g = (Graphics2D) gr;
        super.paintIcon(c, g, x, y);
        Color color;
        DrawingView view = editor.getActiveView();
        Gradient gradient;
        if (view != null && view.getSelectedFigures().size() == 1) {
            gradient = key.get(view.getSelectedFigures().iterator().next());
        } else {
            gradient = key.get(editor.getDefaultAttributes());
        }
        
        int length = gradient.getStopColors().length;
        if(length > stop) {
            color = gradient.getStopColors()[stop];
        }
        else {
            color = editor.getDefaultAttribute(FILL_COLOR);
        }
        
        if (color != null) {
            g.setColor(color);
            g.translate(x, y);
            g.fill(colorShape);
            g.translate(-x, -y);
        }
    }
}
