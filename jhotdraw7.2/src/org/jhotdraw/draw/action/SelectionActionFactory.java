/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw.action;

import java.util.Collection;
import java.util.LinkedList;
import javax.swing.Action;
import org.jhotdraw.app.action.DuplicateAction;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.samples.svg.action.CombineAction;
import org.jhotdraw.samples.svg.action.SplitAction;
import org.jhotdraw.samples.svg.figures.SVGGroupFigure;

/**
 *
 * @author Mads
 */
public class SelectionActionFactory {
    
    public static Collection<Action> createSelectionActions(DrawingEditor editor) {
        LinkedList<Action> a = new LinkedList<Action>();
        a.add(new DuplicateAction());

        a.add(null); // separator

        a.add(new FlipHoriAction(editor));
        a.add(new FlipVertAction(editor));
        
        a.add(null); // separator
        
        a.add(new GroupAction(editor));
        a.add(new UngroupAction(editor));

        a.add(null); // separator

        a.add(new RotationAction(editor));
        a.add(new BringToFrontAction(editor));
        a.add(new SendToBackAction(editor));
        a.add(new EdgeDetectionAction(editor));

        return a;
    }
   
}
