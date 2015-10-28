/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.figures;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.Collection;
import java.util.LinkedList;
import org.jhotdraw.draw.BoundsOutlineHandle;
import org.jhotdraw.draw.Handle;
import org.jhotdraw.draw.ResizeHandleKit;
import org.jhotdraw.draw.TransformHandleKit;
import org.jhotdraw.samples.svg.Gradient;
import org.jhotdraw.samples.svg.SVGAttributeKeys;
import static org.jhotdraw.samples.svg.SVGAttributeKeys.*;


/**
 *
 * @author Mads Svensgaard
 */
public class SVGStarFigure extends SVGAttributedFigure implements SVGFigure{
    
    private StarFigure star;
    
    private transient Shape cachedTransformedShape;
    private transient Shape cachedHitShape;
    //Contructors
    public SVGStarFigure() {
        this(0,0,0,0,0);
    }
    public SVGStarFigure(int x, int y, int r, int innerR, int vertexCount) {
        star = new StarFigure(x, y, r, innerR, vertexCount);
        SVGAttributeKeys.setDefaults(this);
    }
    //Drawing
    @Override
    protected void drawFill(Graphics2D g) {
        if(star.r > 0 && star.innerR > 0) {
            g.fill(star);
        }
    }

    @Override
    protected void drawStroke(Graphics2D g) {
        if(star.r > 0 && star.innerR > 0) {
            g.draw(star);
        }
    }
    //Coordinates and bounds
    @Override
    public Double getBounds() {
        return (Rectangle2D.Double)(star.getBounds2D());
    }

    @Override
    public boolean contains(Point2D.Double p) {
       return getHitShape().contains(p);
    }

    @Override
    public Object getTransformRestoreData() {
        return new Object[]{
            star.clone(),
            TRANSFORM.getClone(this),
        FILL_GRADIENT.getClone(this),
        STROKE_GRADIENT.getClone(this)};
        }

    @Override
    public void restoreTransformTo(Object geometry) {
        throw new UnsupportedOperationException("Not supported yet.");
//        Object[] restoreData = (Object[]) geometry;
//        star =  (Rectangle2D.Double) ((Rectangle2D.Double) restoreData[0]).clone();
//        TRANSFORM.basicSetClone(this, (AffineTransform) restoreData[1]);
//        FILL_GRADIENT.basicSetClone(this, (Gradient) restoreData[2]);
//        STROKE_GRADIENT.basicSetClone(this, (Gradient) restoreData[3]);
//        invalidate();
    }

    @Override
    public void transform(AffineTransform tx) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isEmpty() {
        Rectangle2D.Double d = getBounds();
        return d.height <= 0 || d.width <= 0;
    }

    private Shape getHitShape() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    @Override
    public Collection<Handle> createHandles(int detailLevel) {
        LinkedList<Handle> handles = new LinkedList<Handle>();
        switch (detailLevel % 2) {
            case -1: // Mouse hover handles
                handles.add(new BoundsOutlineHandle(this, false, true));
                break;
            case 0:
                ResizeHandleKit.addResizeHandles(this, handles);
                handles.add(new LinkHandle(this));
                break;
            case 1:
                TransformHandleKit.addTransformHandles(this, handles);
                break;
            default:
                break;
        }
        return handles;
    }
    @Override
    public SVGStarFigure clone() {
        SVGStarFigure that = (SVGStarFigure) super.clone();
        that.star = (StarFigure) this.star.clone();
        that.cachedTransformedShape = null;
        return that;
    }
    @Override
    public void invalidate() {
        super.invalidate();
        cachedTransformedShape = null;
        cachedHitShape = null;
    }
    
}

