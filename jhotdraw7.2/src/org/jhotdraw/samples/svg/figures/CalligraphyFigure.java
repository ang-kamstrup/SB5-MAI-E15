/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.figures;

import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.geom.BezierPath;
import org.jhotdraw.samples.svg.SVGAttributeKeys;

/**
 *
 * This class represents a calligraphy figure. it is basicly the same as a path figure, except the path is doubled
 * 
 * @author jakob
 */
public class CalligraphyFigure extends SVGPathFigure {

    private transient GeneralPath path;
    

    public CalligraphyFigure() {
        add(new SVGBezierFigure());
        SVGAttributeKeys.setDefaults(this);
        
    }


    public CalligraphyFigure(boolean isClosed) {
        if (!isClosed) {
            add(new SVGBezierFigure());
            SVGAttributeKeys.setDefaults(this);
        }
    }

    @Override
    public GeneralPath getPath() {
        if (path == null) {
            path = new GeneralPath();
            for (Figure f : getChildren()){
                SVGBezierFigure n = inverseFigure((SVGBezierFigure) f);
                path.append(n.getBezierPath(), true);
            }
        }
        return path;

    }

    public SVGBezierFigure inverseFigure(SVGBezierFigure figure) {
        
        //Creates clones of the figure, and the last one to the first one backwards with an offset
        SVGBezierFigure clone = (SVGBezierFigure) figure.clone();
        SVGBezierFigure fullPath = (SVGBezierFigure) figure.clone();
        for (int i = 0; i < clone.getNodeCount(); i++) {
            BezierPath.Node n = clone.getNode(clone.getNodeCount() - 1 - i);

            //Sets offset of cp[0]
            n.x[0] += SVGAttributeKeys.OFFSET_X.get(this);
            n.y[0] += SVGAttributeKeys.OFFSET_Y.get(this);


            //Flips all other control point to go opposite directions,
            if (n.x.length > 2) {
                double temp = n.x[2] + SVGAttributeKeys.OFFSET_X.get(this);
                n.x[2] = n.x[1] + SVGAttributeKeys.OFFSET_X.get(this);
                n.x[1] = temp;

                double temp2 = n.y[2] + SVGAttributeKeys.OFFSET_Y.get(this);
                n.y[2] = n.y[1] + SVGAttributeKeys.OFFSET_Y.get(this);
                n.y[1] = temp2;
            }

            fullPath.addNode(i, n);
        }

        return fullPath;
    }


    @Override
    public void drawFill(Graphics2D g) {
        g.fill(getPath());
    }

   
    @Override
    public void drawStroke(Graphics2D g) {
        g.draw(getPath());
    }

}
