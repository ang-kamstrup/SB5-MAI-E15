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
import org.jhotdraw.samples.svg.figures.SVGPathFigure;

/**
 *
 * Tool used to draw a calligraphy figure The figure is just to parallel Bezier
 * Figures
 *
 * @author jakob
 */
public class CalligraphyTool extends BezierTool {

    private SVGPathFigure pathPrototype;

    public CalligraphyTool(SVGPathFigure pathPrototype, SVGBezierFigure bezierProtoype) {
        this(pathPrototype, bezierProtoype, null);
    }

    public CalligraphyTool(SVGPathFigure pathProtoype, SVGBezierFigure bezierFigure, Map<AttributeKey, Object> attributes) {
        super(bezierFigure, attributes);
        this.pathPrototype = pathProtoype;

    }

    protected SVGPathFigure createCal() {
        SVGPathFigure f = (SVGPathFigure) pathPrototype.clone();
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
        SVGPathFigure createdPath = createCal();

        BezierFigure main = createdFigure.clone();
        //copys the current bezier path

        //Creates empty figure for storing inverse path
        SVGBezierFigure inverse = new SVGBezierFigure();

        //Paths TODO
        BezierPath path1 = new BezierPath();
        BezierPath path2 = new BezierPath();


        // goes through every node in main, from the back and adds it to inverse
        for (int i = 0; i < main.getNodeCount(); i++) {

            BezierPath.Node n = main.getNode(main.getNodeCount() - 1 - i);


            //Sets offset of cp[0]
            n.x[0] += offSetX;
            n.y[0] += offSetY;
            
            
            //Flips all other control point to go opposite directions,
            if (n.x.length > 2) {
                double temp = n.x[2] + offSetX;
                n.x[2] = n.x[1] + offSetX;
                n.x[1] = temp;

                double temp2 = n.y[2] + offSetY;
                n.y[2] = n.y[1] + offSetY;
                n.y[1] = temp2;
            } 

         

            
            inverse.addNode(i, n);
        }
        

        //adds an line at beginning of figure, to close it off.
        inverse.addNode(inverse.getNodeCount(), new BezierPath.Node(main.getStartPoint()));

        createdPath.removeAllChildren();

        createdPath.add(main);
        createdPath.add(inverse);

        creationView.getDrawing().add(createdPath);
        fireUndoEvent(createdPath, creationView);
        creationView.addToSelection(createdPath);
        if (isToolDoneAfterCreation()) {
            fireToolDone();
        }

    }
}
