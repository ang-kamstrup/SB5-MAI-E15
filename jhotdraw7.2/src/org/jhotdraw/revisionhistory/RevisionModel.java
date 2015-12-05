/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.revisionhistory;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.jhotdraw.draw.Drawing;

/**
 *
 * @author E
 */
public class RevisionModel {

    private final List<Revision> revisions;

    public RevisionModel() {
	revisions = new LinkedList<Revision>();
    }

    /**
     * Used for saving a revision
     *
     * @param drawing The drawing, which state you want to save
     * @param date The time the revision is saved
     */
    public void add(Drawing drawing, Date date) {
	revisions.add(new Revision(drawing, date));
    }

    /**
     * Used for removing a revision
     *
     * @param revision The instance which is removed
     */
    public void remove(Revision revision) {
	revisions.remove(revision);
    }

    /**
     * @return The list of revisions
     */
    public List<Revision> getRevisions() {
	return revisions;
    }

}
