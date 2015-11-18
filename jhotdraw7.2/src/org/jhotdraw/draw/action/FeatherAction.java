/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw.action;

import java.util.Locale;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.util.ResourceBundleUtil;
import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import org.jhotdraw.undo.*;
import org.jhotdraw.util.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import org.jhotdraw.app.JHotDrawFeatures;
import org.jhotdraw.draw.*;
import org.jhotdraw.geom.*;
import static org.jhotdraw.draw.AttributeKeys.*;

/**
 *
 * @author ChristianArentsen
 */
public class FeatherAction extends AbstractDrawingViewAction {

    private ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels", Locale.getDefault());
    private DrawingView view = getView();
    private DrawingEditor editor;
    private java.awt.Graphics gr;
    private java.awt.Component c;
    private CreationTool ct;
    private AttributeKey<Double> opacityKey;
    private AttributeKey<Color> fillColorKey;
    private AttributeKey<Color> strokeColorKey;
    private Image image;
    private Shape fillShape;
    private Shape strokeShape;

    public FeatherAction(DrawingView view) {
        super(view);
        labels.configureAction(this, "edit.feather");
        setEnabled(true);
    }

    public void actionPerformed(ActionEvent e) {
        Graphics2D g = (Graphics2D) gr;
        //paintIcon(c, g, x, y);


    }

    public void paintIcon(java.awt.Component c, java.awt.Graphics gr, int x, int y) {
        Graphics2D g = (Graphics2D) gr;
        paintIcon(c, g, x, y);
        Double opacity;
        Color fillColor;
        Color strokeColor;
        Figure createdFigure;
        Point anchor = new Point();
        DrawingView view = editor.getActiveView();
        if (view != null && view.getSelectedFigures().size() == 1) {
            opacity = opacityKey.get(view.getSelectedFigures().iterator().next());
            fillColor = (fillColorKey == null) ? null : fillColorKey.get(view.getSelectedFigures().iterator().next());
            strokeColor = (strokeColorKey == null) ? null : strokeColorKey.get(view.getSelectedFigures().iterator().next());
        } else {
            opacity = opacityKey.get(editor.getDefaultAttributes());
            fillColor = (fillColorKey == null) ? null : fillColorKey.get(editor.getDefaultAttributes());
            strokeColor = (strokeColorKey == null) ? null : strokeColorKey.get(editor.getDefaultAttributes());
        }

        if (fillColorKey != null && fillShape != null) {
            if (opacity != null) {
                if (fillColor == null) {
                    fillColor = Color.BLACK;
                }
                g.setColor(new Color((((int) (opacity * 255)) << 24) | (fillColor.getRGB() & 0xffffff), true));
                g.translate(x, y);
                g.fill(fillShape);
                g.translate(-x, -y);
//                createdFigure = ct.createFigure();
//                Point2D.Double p = ct.constrainPoint(ct.viewToDrawing(anchor));
                anchor.x = x;
                anchor.y = y;
//                createdFigure.setBounds(p, p);
//                getDrawing().add(createdFigure);
            }
        }
        if (strokeColorKey != null && strokeShape != null) {
            if (opacity != null) {
                if (strokeColor == null) {
                    strokeColor = Color.BLACK;
                }
                g.setColor(new Color((((int) (opacity * 255)) << 24) | (strokeColor.getRGB() & 0xffffff), true));
                g.translate(x, y);
                g.draw(strokeShape);
                g.translate(-x, -y);
            }
        }
    }
}
