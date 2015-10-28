/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.filters;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.jhotdraw.app.Application;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.action.AbstractSelectedAction;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author Martin
 */
public class BlackHoleFilterAction extends AbstractSelectedAction {
    public final static String ID = "filter.blackHole";
    
    public BlackHoleFilterAction(DrawingEditor editor) {
        super(editor);
    }

    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
