/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import java.awt.Rectangle;
import org.jhotdraw.draw.DrawingView;

/**
 *Strategy pattern: Strategy
 * @author Jesso12
 */
public interface IAreaObserver {
    
    void areaToPrint(Rectangle rectangle, DrawingView view);    
}
