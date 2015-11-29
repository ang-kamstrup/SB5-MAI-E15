/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg;

import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.revisionhistory.RevisionController;

/**
 *
 * @author emilfrisk
 */
public class SVGApplication extends DefaultSDIApplication {

	private static RevisionController revisionController;

	public SVGApplication() {
	}

	@Override
	public void init() {
		super.init();
		
		revisionController = new RevisionController(this);
		revisionController.setupRevisionHistoryCollection();
		revisionController.startRevisionHistoryCollection();
		
		
	}

	public RevisionController getRevisionController(){
		return revisionController;
	}
}
