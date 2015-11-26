/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.action;

import java.awt.event.ActionEvent;
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

	public final static String ID = "view.viewRevisionHistory";

	private final static String DIALOG_CLIENT_PROPERTY = "view.viewSource.dialog";

	public ViewRevisionHistoryAction(Application app) {
		super(app);
		ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
		labels.configureAction(this, ID);
	}

	public void actionPerformed(ActionEvent e) {

		SVGApplication a = (SVGApplication) getApplication();

		RevisionView revisionView = new RevisionView();
		revisionView.setRevisionController(a.getRevisionController());
		revisionView.setup();
		revisionView.setVisible(true);

	}

}
