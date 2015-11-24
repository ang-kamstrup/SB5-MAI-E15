/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.figures;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.geom.RoundRectangle2D;
import java.util.Collection;
import java.util.LinkedList;
import org.jhotdraw.draw.BoundsOutlineHandle;
import org.jhotdraw.draw.Handle;
import org.jhotdraw.draw.ResizeHandleKit;
import org.jhotdraw.draw.TransformHandleKit;
import org.jhotdraw.geom.GrowStroke;
import org.jhotdraw.samples.svg.Gradient;
import org.jhotdraw.samples.svg.SVGAttributeKeys;
import static org.jhotdraw.samples.svg.SVGAttributeKeys.*;

/**
 *
 * @author Mads Svensgaard
 */
public class SVGStarFigure extends SVGAttributedFigure implements SVGFigure {
    
    private StarFigure star;
    private transient Shape cachedHitShape;
    private transient Shape cachedTransformedShape;

    //Contructors
    public SVGStarFigure() {
        this(0, 0, 0, 0);
    }

    public SVGStarFigure(int x, int y, int r, int innerR) {
        
        star = new StarFigure(x, y, r, innerR);
        SVGAttributeKeys.setDefaults(this);
    }
    //Drawing

    @Override
    protected void drawFill(Graphics2D g) {
        if (star.r > 0 && star.innerR > 0) {
            g.fill(star.getDrawingFigure());
        }
    }
    
    @Override
    protected void drawStroke(Graphics2D g) {
        g.draw(star.getDrawingFigure());
    }
    //Coordinates and bounds

    @Override
    public Double getBounds() {
        int width = star.r * 2;
        int height = star.r * 2;
        return new Rectangle2D.Double(star.x-width/2, star.y-height/2, height, width);
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
    public void setBounds(Point2D.Double anchor, Point2D.Double lead) {
        star.x = (int) Math.min(anchor.x, lead.x);
        star.y = (int) Math.min(anchor.y, lead.y);
        star.r = (int) Math.sqrt((lead.x - anchor.x) * (lead.x - anchor.x) + (lead.y - anchor.y) * (lead.y - anchor.y));
        star.innerR = star.r / 2;
        invalidate();
    }
    
    @Override
    public void transform(AffineTransform tx) {
        if (TRANSFORM.get(this) != null || (tx.getType() & (AffineTransform.TYPE_TRANSLATION)) != tx.getType()) {
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
            if (FILL_GRADIENT.get(this) != null
                    && !FILL_GRADIENT.get(this).isRelativeToFigureBounds()) {
                Gradient g = FILL_GRADIENT.getClone(this);
                g.transform(tx);
                FILL_GRADIENT.basicSet(this, g);
            }
            if (STROKE_GRADIENT.get(this) != null
                    && !STROKE_GRADIENT.get(this).isRelativeToFigureBounds()) {
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

    private Shape getTransformedShape() {
        if (cachedTransformedShape == null) {
            if (TRANSFORM.get(this) == null) {
                cachedTransformedShape = star;
            } else {
                cachedTransformedShape = TRANSFORM.get(this).createTransformedShape(star);
            }
        }
        return cachedTransformedShape;
    }
    
    private Shape getHitShape() {
        if (cachedHitShape == null) {
            if (FILL_COLOR.get(this) != null || FILL_GRADIENT.get(this) != null) {
                cachedHitShape = new GrowStroke(
                        (float) SVGAttributeKeys.getStrokeTotalWidth(this) / 2f,
                        (float) SVGAttributeKeys.getStrokeTotalMiterLimit(this)).createStrokedShape(getTransformedShape());
            } else {
                cachedHitShape = SVGAttributeKeys.getHitStroke(this).createStrokedShape(getTransformedShape());
            }
        }
        return cachedHitShape;
    }
    
    @Override
    public Collection<Handle> createHandles(int detailLevel) {
        LinkedList<Handle> handles = new LinkedList<Handle>();
        ResizeHandleKit.addResizeHandles(this, handles);
        handles.add(new LinkHandle(this));
        return handles;
    }

    @Override
    public SVGStarFigure clone() {
        SVGStarFigure that = (SVGStarFigure) super.clone();
        that.star = (StarFigure) this.star.clone();
        return that;
    }
    
    @Override
    public void invalidate() {
        super.invalidate();
        cachedTransformedShape = null;
        cachedHitShape = null;
    }

    @Override
    protected void drawShadow(Graphics2D g) {
    }
}
