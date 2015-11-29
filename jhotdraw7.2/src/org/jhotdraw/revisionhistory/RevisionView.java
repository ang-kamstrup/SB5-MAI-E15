/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.revisionhistory;

import java.awt.BorderLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;

/**
 *
 * @author emilfrisk
 */
public class RevisionView extends javax.swing.JPanel {
	
	public JFrame frame;
	public DefaultListModel listModel;
	public RevisionController revisionController;

	/**
	 * Creates new form RevisionView
	 *
	 */
	public RevisionView() {
		initComponents();
		listModel = (DefaultListModel) revisionList.getModel();
		revisionList.setCellRenderer(new RevisionCellRenderer());
	}
	
	public void setup() {
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		
		for (Revision r : revisionController.getRevisionModel().getRevisions()) {
			this.listModel.addElement(r);
		}
	}
	
	@Override
	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}
	
	public void setRevisionController(RevisionController revisionController) {
		this.revisionController = revisionController;
	}

	/**
	 * This method is called from within the constructor to initialize the
	 * form. WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jScrollPane1 = new javax.swing.JScrollPane();
                revisionList = new javax.swing.JList();

                revisionList.setModel(new DefaultListModel());
                jScrollPane1.setViewportView(revisionList);

                org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                );
        }// </editor-fold>//GEN-END:initComponents
        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JList revisionList;
        // End of variables declaration//GEN-END:variables
}
