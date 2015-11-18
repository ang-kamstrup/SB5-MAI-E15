/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw.action;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;

/**
 *
 * @author Mads
 */
public class RotationAction extends AbstractSelectedAction {
    
    public static String ID = "edit.rotate";
    
   public RotationAction(DrawingEditor editor) {
       super(editor);
       labels.configureAction(this, ID);
   }

    public void actionPerformed(ActionEvent e) {
        final DrawingView view = getView();
        view.setHandleDetailLevel(view.getHandleDetailLevel() + 1);
    }
    
}
