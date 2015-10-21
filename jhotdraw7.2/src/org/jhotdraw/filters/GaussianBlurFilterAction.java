package org.jhotdraw.filters;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author Dan/Kyzaq
 */
public class GaussianBlurFilterAction extends AbstractAction {
    public final static String ID = "filter.gaussianBlur";

    public GaussianBlurFilterAction(){
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.labels");
        labels.configureAction(this, ID);
    }
    
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
