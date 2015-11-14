/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg;

import java.awt.Point;
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
 * Tool used to draw a calligraphy figure
 * The figure is just to parallel Bezier Figures
 * @author jakob
 */
public class CalligraphyTool extends BezierTool{
    
    private SVGPathFigure pathPrototype;
    
    public CalligraphyTool (SVGPathFigure pathPrototype, SVGBezierFigure bezierProtoype) {
        this(pathPrototype,bezierProtoype,null);
    }
    
    public CalligraphyTool(SVGPathFigure pathProtoype, SVGBezierFigure bezierFigure, Map<AttributeKey,Object> attributes){
        super(bezierFigure, attributes);
        this.pathPrototype = pathProtoype;
        
    }
    
    protected SVGPathFigure createCal(){
        SVGPathFigure f = (SVGPathFigure) pathPrototype.clone();
        getEditor().applyDefaultAttributesTo(f);
        if(attributes != null){
            for (Map.Entry<AttributeKey, Object> entry : attributes.entrySet()){
                entry.getKey().basicSet(f, entry.getValue());
            }
        }
        return f;
    }
    
    @Override
    protected void finishCreation(BezierFigure createdFigure, DrawingView creationView){
        creationView.getDrawing().remove(createdFigure);
        SVGPathFigure createdPath = createCal();
        BezierFigure copy = createdFigure.clone();
        
        for (int i=0; i<createdFigure.getNodeCount();i++){
         createdFigure.setPoint(i, new Point.Double((createdFigure.getPoint(i).x)-20,(createdFigure.getPoint(i).y)-10));
        }
        createdFigure.addNode(new BezierPath.Node(copy.getEndPoint()));
        createdFigure.addNode(0, new BezierPath.Node(copy.getStartPoint()));
        createdPath.removeAllChildren();
        createdPath.add(createdFigure);
        createdPath.add(copy);
        creationView.getDrawing().add(createdPath);
        fireUndoEvent(createdPath, creationView);
        creationView.addToSelection(createdPath);
        if (isToolDoneAfterCreation()) {
            fireToolDone();
        }
             
    }
    
}
