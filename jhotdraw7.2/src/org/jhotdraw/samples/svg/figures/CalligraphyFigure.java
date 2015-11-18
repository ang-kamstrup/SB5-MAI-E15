/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.figures;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import java.awt.*;
import java.awt.geom.*;
import org.jhotdraw.app.JHotDrawFeatures;
import org.jhotdraw.draw.*;
import org.jhotdraw.geom.*;
import org.jhotdraw.samples.svg.*;
import static org.jhotdraw.samples.svg.SVGAttributeKeys.*;

/**
 *
 * This class represents a calligraphy figure. it is basically the same as a
 * path figure, except the path is traced back with an offset
 *
 * @author jakob
 */
public class CalligraphyFigure extends SVGPathFigure {

    @FeatureEntryPoint(JHotDrawFeatures.CALI_TOOL)
    public CalligraphyFigure() {
        add(new SVGBezierFigure());
        SVGAttributeKeys.setDefaults(this);



    }

    @FeatureEntryPoint(JHotDrawFeatures.CALI_TOOL)
    public CalligraphyFigure(boolean isClosed) {
        if (!isClosed) {
            add(new SVGBezierFigure());
            SVGAttributeKeys.setDefaults(this);

        }
    }

    @FeatureEntryPoint(JHotDrawFeatures.CALI_TOOL)
    @Override
    public GeneralPath getPath() {
        if (cachedPath == null) {
            cachedPath = new GeneralPath();


            for (Figure f : getChildren()) {
                SVGBezierFigure b = (SVGBezierFigure) f.clone();
                b = CreateFullPath(b);
                cachedPath.append(getArea(b), true);
            }
            cachedPath.setWindingRule(GeneralPath.WIND_NON_ZERO);

        }

        return cachedPath;

    }

    @FeatureEntryPoint(JHotDrawFeatures.CALI_TOOL)
    public SVGBezierFigure CreateFullPath(SVGBezierFigure figure) {

        //Creates clones of the figure, and the last one to the first one backwards with an offset
        SVGBezierFigure clone = (SVGBezierFigure) figure.clone();
//        SVGBezierFigure inverseFigure = new SVGBezierFigure();
        SVGBezierFigure full = (SVGBezierFigure) figure.clone();

        for (int i = 0; i < clone.getNodeCount(); i++) {
            BezierPath.Node n = clone.getNode(clone.getNodeCount() - 1 - i);

            //Sets offset of cp[0]
            n.x[0] += SVGAttributeKeys.OFFSET_X.get(this);
            n.y[0] += SVGAttributeKeys.OFFSET_Y.get(this);


            //Flips all other control point to go opposite directions,
            if (n.x.length > 2) {
                double temp = n.x[2] + OFFSET_X.get(this);
                n.x[2] = n.x[1] + OFFSET_X.get(this);
                n.x[1] = temp;

                double temp2 = n.y[2] + OFFSET_Y.get(this);
                n.y[2] = n.y[1] + OFFSET_Y.get(this);
                n.y[1] = temp2;
            }

            full.addNode(n);

        }

        //adds a node to finish off the figure.
        if (full.getNodeCount() != 0 && clone.getNodeCount() != 0) {
            full.addNode(full.getNodeCount(), new BezierPath.Node(clone.getStartPoint()));

        }
        return full;

    }

    @FeatureEntryPoint(JHotDrawFeatures.CALI_TOOL)
    public Area getArea(SVGBezierFigure figure) {
        //splits the figure into smaller segments between each point and draws them one by one.
        //Done to fix some minor bugs in the code where overlapping would destroy the figure.
        SVGBezierFigure full = (SVGBezierFigure) figure.clone();


        Area x;// = new Area();
        Area y = new Area();
        BezierPath.Node a, b, c, d;
        SVGBezierFigure t1, t2;

        GeneralPath t = new GeneralPath();

        for (int i = 0; i < full.getNodeCount() / 2 - 1; i++) {
            a = full.getNode(i);
            b = full.getNode(i + 1);
            c = full.getNode(full.getNodeCount() - 2 - (i + 1));
            d = full.getNode(full.getNodeCount() - 2 - i);

            t1 = new SVGBezierFigure();
            t2 = new SVGBezierFigure();

            t1.addNode(a);
            t1.addNode(b);
            t2.addNode(c);
            t2.addNode(d);

            t.append(t1.getBezierPath(), false);
            t.append(t2.getBezierPath(), true);


            x = new Area(t);

            y.add(x);

        }

        return y;
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
