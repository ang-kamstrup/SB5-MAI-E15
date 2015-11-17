/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw.action;

import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import org.jhotdraw.draw.DrawingEditor;

/**
 *
 * @author frank
 */
public class CropAction extends AbstractSelectedAction{

    public CropAction(DrawingEditor editor) {
        super(editor);
        setEnabled(true);
    }

    public void actionPerformed(ActionEvent ae) {
        JToggleButton button = (JToggleButton) ae.getSource();
        if(button.isSelected()){
            JOptionPane.showMessageDialog(null, "To use crop tool, click keyboard arrows, to adjust canvas", "Crop tool", JOptionPane.INFORMATION_MESSAGE);
        } else{
            System.out.println("Deselected");
        }
        
    }
}
