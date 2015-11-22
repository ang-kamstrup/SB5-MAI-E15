/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.revisionhistory;

import java.util.LinkedList;
import java.util.List;
import org.jhotdraw.draw.Drawing;

/**
 *
 * @author emilfrisk
 */
public class RevisionController {
    List<Drawing> revisions;
    
    public RevisionController(){
        revisions = new LinkedList<Drawing>();
    }
    
    /**
     * Used for saving a revision
     * @param drawing The drawing, which state you want to save.
     */
    public void saveRevision(Drawing drawing){
        revisions.add(drawing);
    }
    
    /**
     * @return The list of revisions
     */
    public List<Drawing> getRevisions(){
        return revisions;
    }
}
