package org.jhotdraw.draw.action;

import java.awt.event.ActionEvent;
import org.jhotdraw.draw.DrawingEditor;

/**
 * FlipHoriAction.
 * 
 * @author Simon Hansen
 */
public class FlipHoriAction extends AbstractSelectedAction {
    public final static String ID = "edit.fliphori";
    
    /** Creates a new instance. */
    public FlipHoriAction(DrawingEditor editor) {
        super(editor);
        labels.configureAction(this, ID);
    }

    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
