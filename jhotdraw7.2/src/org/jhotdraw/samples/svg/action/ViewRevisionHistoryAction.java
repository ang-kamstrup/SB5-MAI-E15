/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.action;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.action.AbstractViewAction;
import org.jhotdraw.revisionhistory.RevisionView;
import org.jhotdraw.samples.svg.SVGApplication;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author E
 */
public class ViewRevisionHistoryAction extends AbstractViewAction {

	/**
	 * The ViewRevisionHistoryActions ID, which it is represented by in the
	 * SVGApplicationModels "actions" HashMap.
	 */
	public final static String ID = "view.viewRevisionHistory";

	public ViewRevisionHistoryAction(Application app) {
		super(app);
		ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
		labels.configureAction(this, ID);
	}

	/**
	 * The operation which happens when the action is performed. In this
	 * case it shows the RevisionView.
	 * @param e Represent the Event instance - in this case an ActionEvent.
	 */
	public void actionPerformed(ActionEvent e) {

		SVGApplication a = (SVGApplication) getApplication();

		RevisionView revisionView = new RevisionView();
		revisionView.setRevisionController(a.getRevisionController());
		revisionView.setup();
		final ViewRevisionHistoryAction self = this;
		revisionView.frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				self.setEnabled(true);
			}
		});
		revisionView.setVisible(true);
		this.setEnabled(false);
	}
}
