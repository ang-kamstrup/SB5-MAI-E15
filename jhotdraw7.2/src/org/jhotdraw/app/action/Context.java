/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import java.awt.Rectangle;
import org.jhotdraw.draw.DrawingView;

/**
 * Strategy patten context
 * @author Jesso12
 */
public class Context {
    private IAreaObserver iareaObserver;
    
    public Context(IAreaObserver iareaObserver){
        this.iareaObserver = iareaObserver;
    }
    
    public void areaToPrint(Rectangle rectangle, DrawingView view){
        iareaObserver.areaToPrint(rectangle, view);
    }
    
}
