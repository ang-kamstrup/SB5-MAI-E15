/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg;

import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.revisionhistory.RevisionController;
import org.jhotdraw.revisionhistory.RevisionView;

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

		revisionController = new RevisionController();
		startRevisionHistory();

		/*
		revisionView = new RevisionView();
		revisionView.setup();
		revisionView.setVisible(true);
		revisionView.setRevisionController(revisionController);
		 */
	}

	public void startRevisionHistory() {
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException ex) {
						//TODO: gør noget ved denne
					}

					SVGView svgView = (SVGView) getActiveView();
					revisionController.saveRevision(svgView.getDrawing());
				}
			}
		}).start();
	}

	public RevisionController getRevisionController(){
		return revisionController;
	}
}
