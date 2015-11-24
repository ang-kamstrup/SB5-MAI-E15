/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.figures;

import java.awt.Polygon;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

/**
 *
 * @author Mads
 */
public class StarFigure extends Polygon implements Cloneable {
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getR() {
        return r;
    }

    public int getInnerR() {
        return innerR;
    }

    public static int getVertexCount() {
        return vertexCount;
    }

    public double getStartAngle() {
        return startAngle;
    }
    int x;
    int y;
    int r;
    int innerR;
    static int vertexCount = 5;
    double startAngle;
    
    public StarFigure(int x, int y, int r, int innerR) {
        this(x,y,r,innerR,vertexCount,0);
        this.x = x;
        this.y = y;
        this.r = r;
        this.innerR = innerR;
        startAngle = 0;
        
    }
    
    public StarFigure(int x, int y, int r, int innerR, int vertexCount, int startAngle) {
        super(getXCoordinates(x, y, r, innerR, startAngle), getYCoordinates(x, y, r, innerR, vertexCount, startAngle), vertexCount);
    }

    protected static int[] getXCoordinates(int x, int y, int r, int innerR, double startAngle) {
        int res[] = new int[vertexCount * 2];
        double addAngle = 2 * Math.PI / vertexCount;
        double angle = startAngle;
        double innerAngle = startAngle + Math.PI / vertexCount;
        for (int i = 0; i < vertexCount; i++) {
            res[i * 2] = (int) Math.round(r * Math.cos(angle)) + x;
            angle += addAngle;
            res[i * 2 + 1] = (int) Math.round(innerR * Math.cos(innerAngle)) + x;
            innerAngle += addAngle;
        }
        return res;
    }

    protected static int[] getYCoordinates(int x, int y, int r, int innerR, int vertexCount, double startAngle) {
        int res[] = new int[vertexCount * 2];
        double addAngle = 2 * Math.PI / vertexCount;
        double angle = startAngle;
        double innerAngle = startAngle + Math.PI / vertexCount;
        for (int i = 0; i < vertexCount; i++) {
            res[i * 2] = (int) Math.round(r * Math.sin(angle)) + y;
            angle += addAngle;
            res[i * 2 + 1] = (int) Math.round(innerR * Math.sin(innerAngle)) + y;
            innerAngle += addAngle;
        }
        return res;
    }
    protected GeneralPath getDrawingFigure() {
        double angle = Math.PI / vertexCount;
        
        GeneralPath path = new GeneralPath();
        
        for (int i = 0; i < 2 * vertexCount; i++) {
            double k = (i & 1) == 0 ? r : innerR;
            Point2D.Double p = new Point2D.Double(x + Math.cos(i * angle) * k, y + Math.sin(i * angle) * k);
            if (i == 0) {
                path.moveTo(p.getX(), p.getY());
            } else {
                path.lineTo(p.getX(), p.getY());
            }
        }
        path.closePath();
        return(path);
    }
    @Override
    public Object clone() {
	try {
	    return super.clone();
	} catch (CloneNotSupportedException e) {
	    throw new InternalError();
	}
    }
}
