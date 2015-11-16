/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg;

import java.util.Map;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.BezierFigure;
import org.jhotdraw.draw.BezierTool;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.geom.BezierPath;

import org.jhotdraw.samples.svg.figures.SVGBezierFigure;
import org.jhotdraw.samples.svg.figures.CalligraphyFigure;


/**
 *
 * Tool used to draw a calligraphy figure The figure is just to parallel Bezier
 * Figures
 *
 * @author jakob
 */
public class CalligraphyTool extends BezierTool {

    private CalligraphyFigure pathPrototype;

    public CalligraphyTool(CalligraphyFigure pathPrototype, SVGBezierFigure bezierProtoype) {
        this(pathPrototype, bezierProtoype, null);
    }

    public CalligraphyTool(CalligraphyFigure pathProtoype, SVGBezierFigure bezierFigure, Map<AttributeKey, Object> attributes) {
        super(bezierFigure, attributes);
        this.pathPrototype = pathProtoype;

    }

    protected CalligraphyFigure createCal() {
        CalligraphyFigure f = (CalligraphyFigure) pathPrototype.clone();
        getEditor().applyDefaultAttributesTo(f);
        if (attributes != null) {
            for (Map.Entry<AttributeKey, Object> entry : attributes.entrySet()) {
                entry.getKey().basicSet(f, entry.getValue());
            }
        }
        return f;
    }

    @Override
    protected void finishCreation(BezierFigure createdFigure, DrawingView creationView) {
        int offSetX = -30;
        int offSetY = -20;

        BezierPath.Node current;

        creationView.getDrawing().remove(createdFigure);
        CalligraphyFigure createdPath = createCal();

        BezierFigure main = createdFigure.clone();
    
        createdPath.add(main);
        creationView.getDrawing().add(createdPath);
        fireUndoEvent(createdPath, creationView);
        creationView.addToSelection(createdPath);
        if (isToolDoneAfterCreation()) {
            fireToolDone();
        }

    }
}
