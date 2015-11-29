/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.revisionhistory;

import java.awt.Component;
import java.text.SimpleDateFormat;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author E
 */
public class RevisionCellRenderer extends JLabel implements ListCellRenderer<Revision> {

	public Component getListCellRendererComponent(JList<? extends Revision> list, Revision value, int index, boolean isSelected, boolean cellHasFocus) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		setText(dateFormat.format(value.getDate()));
		return this;
	}
	
}
