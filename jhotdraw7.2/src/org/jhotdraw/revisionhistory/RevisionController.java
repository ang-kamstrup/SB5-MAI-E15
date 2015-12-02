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

	private final Application application;

	/**
	 * The thread in which the operations to collect our revisions happen.
	 */
	private Thread revisionCollectionThread;

	private final long TENMIN = 600000;

	public RevisionController(Application application) {
		revisionModel = new RevisionModel();
		this.application = application;
	}

	/**
	 * Variable determining if we are collecting Revision data or not.
	 */
	private Boolean isCollecting = true;

	/**
	 * Operation setting up the revisionCollectionThread for collection of
	 * Revisions.
	 */
	public void setupRevisionHistoryCollection() {
		revisionCollectionThread = new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException ex) {
						//TODO: gør noget ved denne
					}

					if (isCollecting) {
						SVGView svgView = (SVGView) application.getActiveView();
						Drawing d = (Drawing) svgView.getDrawing().clone();
						Date date = new Date();
						revisionModel.add(d, date);
					}
					
					final Date NOW = new Date();
					int i = 1;
					for (Revision r : revisionModel.getRevisions()){
						if (r.getDate().before(NOW)){
							if (2%i == 1) revisionModel.remove(r);
						}
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

	/**
	 * Holds "old" drawing while preview is going on.
	 */
	private Drawing oldDrawing;

	/**
	 * Preview a revision of the drawing
	 * @param revision The instance set for previewing
	 */	
	public void previewRevision(Revision revision){
		SVGView view = (SVGView) application.getActiveView();
		oldDrawing = view.getDrawing();
		view.setDrawing(revision.getDrawing());
		view.revalidate();
		view.repaint();
	}
	
	/**
	 * Stops the preview and shows the "old" drawing
	 */
	public void stopPreview(){
		SVGView view = (SVGView) application.getActiveView();
		view.setDrawing(oldDrawing);
	}
}
