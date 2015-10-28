/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.filters;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import org.jhotdraw.app.Application;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.action.AbstractSelectedAction;
import org.jhotdraw.draw.action.AttributeAction;
import org.jhotdraw.draw.action.LineDecorationIcon;
import org.jhotdraw.gui.JPopupButton;

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
        //JOptionPane.showInputDialog("hoi");
        //JOptionPane.showInputDialog(this, "", "Pixel Filter", PLAIN_MESSAGE, null, selectionValues, enabled);
        /*
         * parentComponent - the parent Component for the dialog 
         * message  - the Object to display 
         * title - the String to display in the dialog title bar 
         * messageType - the type of message to be displayed: ERROR_MESSAGE, INFORMATION_MESSAGE, WARNING_MESSAGE, QUESTION_MESSAGE, or PLAIN_MESSAGE 
         * icon - the Icon image to display 
         * selectionValues - an array of Objects that gives the possible selections 
         * initialSelectionValue - the value used to initialize the input field
         */
      
    }
    
}
