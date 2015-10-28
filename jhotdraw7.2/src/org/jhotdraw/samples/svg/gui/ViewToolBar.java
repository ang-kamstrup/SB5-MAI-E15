/*
 * @(#)CanvasToolBar.java  1.0  2008-05-18
 *
 * Copyright (c) 2007-2008 by the original authors of JHotDraw
 * and all its contributors.
 * All rights reserved.
 *
 * The copyright of this software is owned by the authors and  
 * contributors of the JHotDraw project ("the copyright holders").  
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * the copyright holders. For details see accompanying license terms. 
 */
package org.jhotdraw.samples.svg.gui;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import java.beans.PropertyChangeEvent;
import javax.swing.border.*;
import org.jhotdraw.util.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.util.prefs.Preferences;
import javax.swing.*;
import org.jhotdraw.app.JHotDrawFeatures;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.GridConstrainer;
import org.jhotdraw.draw.action.*;
import org.jhotdraw.gui.JLifeFormattedTextField;
import org.jhotdraw.gui.plaf.palette.*;
import org.jhotdraw.text.JavaNumberFormatter;

/**
 * ViewToolBar.
 * <p>
 * Note: you must explicitly set the view before createDisclosedComponents is
 * called for the first time.
 *
 * @author Werner Randelshofer
 * @version 1.0 2008-05-18 Created.
 */
public class ViewToolBar extends AbstractToolBar {

    private DrawingView view;
    private ResourceBundleUtil labels;

    /** Creates new instance. */
    public ViewToolBar() {
        this.labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        setName(labels.getString(getID() + ".toolbar"));
        setDisclosureStateCount(3);
    }

    public void setView(DrawingView view) {
        this.view = view;
        prefs = Preferences.userNodeForPackage(getClass());
        GridConstrainer constrainer = (GridConstrainer) view.getVisibleConstrainer();
        constrainer.setHeight(prefs.getDouble("view.gridSize", 8d));
        constrainer.setWidth(prefs.getDouble("view.gridSize", 8d));
    }
    
    /**
     * Adds a new "Toggle grid" button to the toolbar.
     * @param panel to add button to.
     * @param extended denotes whether the toolbar is in extended mode.
     */
    private void addToggleGridButton(JPanel panel, boolean extended) {
        GridBagConstraints gbc;
        AbstractButton btn;
        
        if(!extended) {
            btn = ButtonFactory.createToggleGridButton(view);
            btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
            labels.configureToolBarButton(btn, "alignGrid");

            gbc = new GridBagConstraints();
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.FIRST_LINE_START;
            gbc.fill = GridBagConstraints.NONE;
            gbc.insets = new Insets(0, 0, 0, 0);

            panel.add(btn, gbc);

        } else {
            // Grid button
            JLifeFormattedTextField gridSizeField = new JLifeFormattedTextField();
            gridSizeField.setColumns(3);
            gridSizeField.setToolTipText(labels.getString("view.gridSize.toolTipText"));
            gridSizeField.setHorizontalAlignment(JLifeFormattedTextField.RIGHT);
            gridSizeField.putClientProperty("Palette.Component.segmentPosition", "first");
            gridSizeField.setUI((PaletteFormattedTextFieldUI) PaletteFormattedTextFieldUI.createUI(gridSizeField));
            gridSizeField.setFormatterFactory(JavaNumberFormatter.createFormatterFactory(0d, 1000d, 1d, true, false));
            gridSizeField.setHorizontalAlignment(JTextField.LEADING);
            final GridConstrainer constrainer = (GridConstrainer) view.getVisibleConstrainer();
            gridSizeField.addPropertyChangeListener(new PropertyChangeListener() {

                public void propertyChange(PropertyChangeEvent evt) {
                    if (evt.getPropertyName().equals("value")) {
                        if (evt.getNewValue() != null) {
                            constrainer.setWidth((Double) evt.getNewValue());
                            constrainer.setHeight((Double) evt.getNewValue());
                            prefs = Preferences.userNodeForPackage(getClass());
                            prefs.putDouble("view.gridSize", (Double) evt.getNewValue());
                            view.getComponent().repaint();
                        }
                    }
                }
            });
            gridSizeField.setValue(constrainer.getHeight());

            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(0, 0, 0, 0);
            gbc.anchor = GridBagConstraints.FIRST_LINE_START;
            panel.add(gridSizeField, gbc);

            // Toggle grid part
            btn = ButtonFactory.createToggleGridButton(view);
            btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
            labels.configureToolBarButton(btn, "alignGrid");
            gbc = new GridBagConstraints();
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.FIRST_LINE_START;
            gbc.fill = GridBagConstraints.NONE;
            gbc.insets = new Insets(0, 0, 0, 0);
            panel.add(btn, gbc);
        }
    }
    
    /**
     * Adds a new "Zoom" button to the toolbar.
     * @param panel to add button to.
     */
    private void addZoomButton(JPanel panel, boolean extended) {
        GridBagConstraints gbc;
        AbstractButton btn;
        
        if(!extended) {
            btn = ButtonFactory.createZoomButton(view);
            btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
            labels.configureToolBarButton(btn, "view.zoomFactor");
            btn.setText("100 %");

            gbc = new GridBagConstraints();
            gbc.gridx = gbc.gridy = 1;
            gbc.weighty = gbc.weightx = 1;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.FIRST_LINE_START;
            gbc.insets = new Insets(3, 0, 0, 0);        

            btn.setPreferredSize(new Dimension(btn.getPreferredSize().width, btn.getPreferredSize().height));
            panel.add(btn, gbc);
        } else {
            
            // Text input for zoom
            final JLifeFormattedTextField scaleFactorField = new JLifeFormattedTextField();
            scaleFactorField.setColumns(3);
            scaleFactorField.setToolTipText(labels.getString("view.zoomFactor.toolTipText"));
            scaleFactorField.setHorizontalAlignment(JLifeFormattedTextField.RIGHT);
            scaleFactorField.putClientProperty("Palette.Component.segmentPosition", "first");
            scaleFactorField.setUI((PaletteFormattedTextFieldUI) PaletteFormattedTextFieldUI.createUI(scaleFactorField));
            scaleFactorField.setFormatterFactory(JavaNumberFormatter.createFormatterFactory(0.01d, 50d, 100d, true, false));
            scaleFactorField.setHorizontalAlignment(JTextField.LEADING);
            scaleFactorField.setValue(view.getScaleFactor());
            scaleFactorField.addPropertyChangeListener(new PropertyChangeListener() {

                public void propertyChange(PropertyChangeEvent evt) {
                    if (evt.getPropertyName().equals("value")) {
                        if (evt.getNewValue() != null) {
                            view.setScaleFactor((Double) evt.getNewValue());
                        }
                    }
                }
            });
            view.addPropertyChangeListener(new PropertyChangeListener() {

                public void propertyChange(PropertyChangeEvent evt) {
                    if (evt.getPropertyName()==DrawingView.SCALE_FACTOR_PROPERTY) {
                        if (evt.getNewValue() != null) {
                            scaleFactorField.setValue((Double) evt.getNewValue());
                        }
                    }
                }
            }); 
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.insets = new Insets(3, 0, 0, 0);
            gbc.anchor = GridBagConstraints.FIRST_LINE_START;
            panel.add(scaleFactorField, gbc);
            
            // Zoom factor button
            btn = ButtonFactory.createZoomButton(view);
            btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
            labels.configureToolBarButton(btn, "view.zoomFactor");
            btn.setText("100 %");
            gbc = new GridBagConstraints();
            gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.FIRST_LINE_START;
            gbc.insets = new Insets(3, 0, 0, 0);
            gbc.weighty = 1;
            btn.setPreferredSize(new Dimension(btn.getPreferredSize().width, scaleFactorField.getPreferredSize().height));
            panel.add(btn, gbc);
        }
    }

    @Override
    @FeatureEntryPoint(JHotDrawFeatures.VIEW_PALETTE)
    protected JComponent createDisclosedComponent(int state) {
        JPanel p = new JPanel();
        p.setOpaque(false);

        p.removeAll();
        p.setBorder(new EmptyBorder(5, 5, 5, 8));
        GridBagLayout layout = new GridBagLayout();
        p.setLayout(layout);

        // State is used to refer to if the accordian toolbar is:
        // 1: Opened slightly
        // 2: Completely open
        switch (state) {
            case 1: {
                addToggleGridButton(p, false);
                addZoomButton(p, false);
            } break;
            case 2: {
                addToggleGridButton(p, true);
                addZoomButton(p, true);
            } break;
        }
        return p;
    }

    @Override
    protected String getID() {
        return "view";
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setOpaque(false);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
