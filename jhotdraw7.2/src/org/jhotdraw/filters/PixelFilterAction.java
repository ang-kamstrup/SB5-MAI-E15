/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.filters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.LinkedList;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.action.AbstractSelectedAction;
import org.jhotdraw.gui.JAttributeSlider;

import static org.jhotdraw.samples.svg.SVGAttributeKeys.*;
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
    
    public PixelFilterAction(DrawingEditor editor) {
        super(editor);
    }

    public void actionPerformed(ActionEvent ae) {
        createWindow();
    }
    
    protected void createWindow(){
        
        int sliderStartValue = 50;
        
        // Textfield
        selectedValue = new JTextField(""+sliderStartValue, 3);
        // Slider
        slider = new JAttributeSlider(JSlider.HORIZONTAL, 0, 100, sliderStartValue);
        
        // Action Listeners
        slider.addChangeListener(new SliderListener());
        selectedValue.addActionListener(new SelectedValueActionListener()); 
        
        // Objects to be put into the JOptionPane
        Object[] options = { "OK", "CANCEL", slider, selectedValue};
        
        // Creation of the JOptionPane
        pane = new JOptionPane();
        int choice = pane.showOptionDialog(new JFrame(), "Select pixel ratio, and click OK to continue", "Pixelation",
        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
        null, options, JOptionPane.YES_OPTION);
        
        // OK = 0
        if (choice == 0){
            
            DrawingView tempView = getView();
            LinkedList<Figure> figures = new LinkedList<Figure>(tempView.getSelectedFigures());
        
            // get variable to change
            
            for (Figure fig : figures){
                if (fig instanceof SVGImageFigure){
                pixelate(fig);
                }
                
            }
            
            // Deselect all objects for updating
            tempView.clearSelection();
            // loop through previous figures and add them back to selection
            for (Figure fig : figures){
                if (fig instanceof SVGImageFigure){
                tempView.toggleSelection(fig);
                }
                
            }
        }
        
        
        // CANCEL = 1
        else if (choice == 1){
            // Not implemented.
        }
        
      
        
      
    }
    
    void pixelate(Figure fig){
        Pixelation pix = new Pixelation(); 
        pix.pixelateImage((SVGImageFigure) fig, slider.getValue());
    }
    
    private class SliderListener implements ChangeListener {
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting()) {
            selectedValue.setText(""+source.getValue());
        }    
    }
}
    private class SelectedValueActionListener implements ActionListener {
    
        public void actionPerformed(ActionEvent e) {        
        if (selectedValue.getText().matches("-?\\d+(\\.\\d+)?") ){
            int a = Integer.parseInt(selectedValue.getText());
            slider.setValue(a);
            
         }
        }
    }
 
}
