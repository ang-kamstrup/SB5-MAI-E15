/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.filters;

import java.awt.event.ActionEvent;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.action.AbstractSelectedAction;

/**
 *
 * @author Martin
 */
public class PixelFilterAction extends AbstractSelectedAction {
    public final static String ID = "filter.pixel";
    
    public PixelFilterAction(DrawingEditor editor) {
        super(editor);
    }

    public void actionPerformed(ActionEvent ae) {
        createWindow();
    }
    
    protected void createWindow(){
     //MayDrawApp window = new MyDrawApp();
     //window.open();
    }
    
}
