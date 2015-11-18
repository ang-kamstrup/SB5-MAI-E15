/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw.action;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import static org.jhotdraw.draw.AttributeKeys.*;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.gui.DrawingAttributeEditorHandler;
import org.jhotdraw.gui.JAttributeTextField;

/**
 *
 * @author frank
 */
public class CropAction extends AbstractSelectedAction{

    private JAttributeTextField<Double> widthField, heightField;
    private DrawingEditor editor;
    
    public CropAction(DrawingEditor editor) {
        super(editor);
        setEnabled(true);
        this.editor = editor;
        widthField = new JAttributeTextField<Double>();
        heightField = new JAttributeTextField<Double>();
        widthField.setAttributeValue(200.00);
        heightField.setAttributeValue(200.00);
    }

    public void actionPerformed(ActionEvent ae) {
        JToggleButton button = (JToggleButton) ae.getSource();
        if(button.isSelected()){
            JOptionPane.showMessageDialog(null, "To use crop tool, click keyboard arrows, to adjust canvas", "Crop tool", JOptionPane.INFORMATION_MESSAGE);
//            Double cw = CANVAS_WIDTH.get(getDrawing());
//            Double ch = CANVAS_HEIGHT.get(getDrawing());
//            System.out.println("Width: " + cw + " Height: " + ch);
        } else{
            System.out.println("Deselected");
        }
        
    }
}
