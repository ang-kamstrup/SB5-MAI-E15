package org.jhotdraw.draw.action;

import java.awt.event.ActionEvent;
import java.util.Locale;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author tecor13
 */
public class insertGraph extends AbstractSelectedAction{
    
    private ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels", Locale.getDefault());

    insertGraph(DrawingEditor editor) {
        super(editor);
        labels.configureAction(this, "edit.insertGraph");
        
    }

    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
