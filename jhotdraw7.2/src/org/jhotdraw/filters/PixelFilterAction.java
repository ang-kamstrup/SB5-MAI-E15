/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.filters;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author Martin
 */
public class PixelFilterAction extends AbstractAction {
    public final static String ID = "filter.pixel";
    
    public PixelFilterAction(){
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.labels");
        labels.configureAction(this, ID);
    }

    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
