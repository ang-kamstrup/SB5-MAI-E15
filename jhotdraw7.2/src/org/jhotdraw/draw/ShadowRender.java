/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import org.jhotdraw.geom.Dimension2DDouble;
import org.jhotdraw.samples.svg.figures.SVGEllipseFigure;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;

/**
 *
 * @author S
 */
public class ShadowRender {
    public ShadowRender(){
        
    }
    public static Shape DrawShadow(Graphics2D g, Figure fig, Dimension2DDouble offset){
        Shape shadow = null;
        //instance.getClass().getSimpleName()
        if(fig instanceof SVGRectFigure){
            SVGRectFigure rFig = (SVGRectFigure) fig;
            shadow = new Rectangle2D.Double(rFig.getX() + offset.width, rFig.getY() + offset.height, 
                    rFig.getWidth(), rFig.getHeight());

        } else if(fig instanceof SVGEllipseFigure){
            SVGEllipseFigure eFig = (SVGEllipseFigure) fig;
            shadow = new Ellipse2D.Double(eFig.getX()+ offset.width, eFig.getY()+ offset.height, 
                        eFig.getWidth(), eFig.getHeight());
        }
        if(shadow != null && g != null){
            g.fill(shadow);
            g.draw(shadow);
            
        }
        return shadow;
    }
}
