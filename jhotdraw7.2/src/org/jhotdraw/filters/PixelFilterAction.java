/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.filters;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jhotdraw.app.JHotDrawFeatures;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.action.AbstractSelectedAction;
import org.jhotdraw.gui.JAttributeSlider;
import org.jhotdraw.samples.svg.figures.SVGImageFigure;

/**
 *
 * @author Martin
 */
public class PixelFilterAction extends AbstractSelectedAction {

    public final static String ID = "filter.pixel";
    public static JTextField selectedValue;
    public static JAttributeSlider slider;
    public static JButton OK, CANCEL;
    public static JOptionPane pane;
    private final int sliderStartValue = 50;

    public PixelFilterAction(DrawingEditor editor) {
        super(editor);
    }

    @FeatureEntryPoint(JHotDrawFeatures.PIXELATE)
    public void actionPerformed(ActionEvent ae) {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                createWindow();
            }
        });
        thread.start();
    }

    protected void createWindow() {

        // Textfield & Slider
        selectedValue = new JTextField("" + sliderStartValue, 3);
        slider = new JAttributeSlider(JSlider.HORIZONTAL, 0, 100, sliderStartValue);

        // Action Listeners
        slider.addChangeListener(new SliderListener());
        selectedValue.addActionListener(new SelectedValueActionListener());

        // Objects to be put into the JOptionPane
        Object[] options = {"OK", "CANCEL", slider, selectedValue};

        // Creation of the JOptionPane
        pane = new JOptionPane();
        int choice = pane.showOptionDialog(new JFrame(), "Select pixel ratio, and click OK to continue", "Pixelation",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, JOptionPane.YES_OPTION);

        // OK = 0
        if (choice == 0) {
            // Slider value
            int value = slider.getValue();

            // Slider value cannot be 0
            if (value == 0) {
                value = 1;
            }
            DrawingView tempView = getView();
            LinkedList<Figure> figures = new LinkedList<Figure>(tempView.getSelectedFigures());

            // Perform Pixelation
            // Change images
            Pixelation pix = new Pixelation();
            for (Figure fig : figures) {
                if (fig instanceof SVGImageFigure) {
                    pixelate(pix, fig, value);
                }

            }

            updateCanvas(figures, tempView);


        } // CANCEL = 1
        else if (choice == 1) {
            // Not implemented.
        }




    }

    void pixelate(Pixelation pix, Figure fig, int value) {
        pix.pixelateImage((SVGImageFigure) fig, value);
    }
    

    private void updateCanvas(LinkedList<Figure> figures, DrawingView tempView) {
        // Deselect all objects for updating
        tempView.clearSelection();
        // loop through previous figures and add them back to selection
        for (Figure fig : figures) {
            if (fig instanceof SVGImageFigure) {
                tempView.toggleSelection(fig);
            }

        }
    }
    
    
    private class SliderListener implements ChangeListener {

        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting()) {
                selectedValue.setText("" + source.getValue());
            }
        }
    }

    private class SelectedValueActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            // Check if it's a legit symbol
            if (selectedValue.getText().matches("-?\\d+(\\.\\d+)?")) {
                int a = Integer.parseInt(selectedValue.getText());
                slider.setValue(a);

            }
        }
    }
}
