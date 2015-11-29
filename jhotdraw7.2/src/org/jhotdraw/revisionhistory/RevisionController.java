/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.revisionhistory;

import java.util.Date;
import org.jhotdraw.app.Application;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.samples.svg.SVGView;

/**
 *
 * @author emilfrisk
 */
public class RevisionController {

	/**
	 * The model, which holds our data, in this case Revisions.
	 */
	private final RevisionModel revisionModel;

	/**
	 * The thread in which the operations to collect our revisions happen.
	 */
	private Thread revisionCollectionThread;

	public RevisionController() {
		revisionModel = new RevisionModel();
	}

	/**
	 * Variable determining if we are collecting Revision data or not.
	 */
	private Boolean isCollecting = true;

	/**
	 * Operation setting up the revisionCollectionThread for collection of
	 * Revisions.
	 *
	 * @param app The application instance which runs everything.
	 */
	public void setupRevisionHistoryCollection(final Application app) {
		revisionCollectionThread = new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException ex) {
						//TODO: gør noget ved denne
					}

					if (isCollecting) {
						SVGView svgView = (SVGView) app.getActiveView();
						Drawing d = svgView.getDrawing();
						Date date = new Date();
						revisionModel.saveRevision(d, date);
					}
				}
			}
		});
	}

	/**
	 * Variable defining if thread is started, assuring that some operations
	 * are only run once.
	 */
	private Boolean threadStarted = false;

	/**
	 * Start the collection of revisions
	 */
	public void startRevisionHistoryCollection() {
		if (!threadStarted) {
			revisionCollectionThread.start();
			threadStarted = true;
			return;
		}

		isCollecting = true;
	}

	/**
	 * Pause the collection of revisions
	 */
	public void pauseRevisionHistoryCollection() {
		isCollecting = false;
	}

	/**
	 * @return The model containing the Revisions.
	 */
	public RevisionModel getRevisionModel() {
		return revisionModel;
	}

}
