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
       double height = star.r*2;
       double width = star.r*2;
       return new Rectangle2D.Double(star.x, star.y, height, width);
//        return (Rectangle2D.Double)(star.getBounds2D());
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
        Object[] restoreData = (Object[]) geometry;
        star = (StarFigure) restoreData[0];
        TRANSFORM.basicSetClone(this, (AffineTransform) restoreData[1]);
        FILL_GRADIENT.basicSetClone(this, (Gradient) restoreData[2]);
        STROKE_GRADIENT.basicSetClone(this, (Gradient) restoreData[3]);
        invalidate();
    }

    @Override
    public void transform(AffineTransform tx) {
        invalidateTransform();
        if(TRANSFORM.get(this) != null || (tx.getType() & (AffineTransform.TYPE_TRANSLATION)) != tx.getType()) {
            if (TRANSFORM.get(this) == null) {
                TRANSFORM.basicSet(this, (AffineTransform) tx.clone());
            } else {
                AffineTransform t = TRANSFORM.getClone(this);
                t.preConcatenate(tx);
                TRANSFORM.basicSet(this, t);
            } 
        } else {
            Point2D.Double anchor = getStartPoint();
            Point2D.Double lead = getEndPoint();
            setBounds(
                    (Point2D.Double) tx.transform(anchor, anchor),
                    (Point2D.Double) tx.transform(lead, lead));
            if (FILL_GRADIENT.get(this) != null &&
                    !FILL_GRADIENT.get(this).isRelativeToFigureBounds()) {
                Gradient g = FILL_GRADIENT.getClone(this);
                g.transform(tx);
                FILL_GRADIENT.basicSet(this, g);
            }
            if (STROKE_GRADIENT.get(this) != null &&
                    !STROKE_GRADIENT.get(this).isRelativeToFigureBounds()) {
                Gradient g = STROKE_GRADIENT.getClone(this);
                g.transform(tx);
                STROKE_GRADIENT.basicSet(this, g);
            }
        }
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
        invalidateTransform();
    }
    private void invalidateTransform() {
        cachedTransformedShape = null;
        cachedHitShape = null;
    }
    
}

