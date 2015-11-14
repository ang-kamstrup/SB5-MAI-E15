package org.jhotdraw.draw.action;

import java.awt.event.ActionEvent;
import org.jhotdraw.draw.DrawingEditor;

/**
 * FlipVertAction.
 * 
 * @author Simon Hansen
 */
public class FlipVertAction extends AbstractSelectedAction{
    public final static String ID = "edit.flipvert";
    
    /** Creates a new instance. */
    public FlipVertAction(DrawingEditor editor) {
        super(editor);
        labels.configureAction(this, ID);
    }

    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
