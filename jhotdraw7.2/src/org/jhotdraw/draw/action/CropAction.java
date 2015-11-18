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
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.gui.DrawingAttributeEditorHandler;
import org.jhotdraw.gui.JAttributeTextField;

/**
 *
 * @author frank
 */
public class CropAction extends AbstractSelectedAction{

    private DrawingEditor editor;
    private DrawingView view;
    
    public CropAction(DrawingEditor editor, DrawingView view) {
        super(editor);
        setEnabled(true);
        this.editor = editor;
        this.view = view;
    }

    public void actionPerformed(ActionEvent ae) {
        JToggleButton button = (JToggleButton) ae.getSource();
        if(button.isSelected()){
            JOptionPane.showMessageDialog(null, "To use crop tool, click keyboard arrows, to adjust canvas", "Crop tool", JOptionPane.INFORMATION_MESSAGE);
            view.getDrawing().setAttribute(CANVAS_WIDTH, 200.00);
            view.getDrawing().setAttribute(CANVAS_HEIGHT, 200.00);
        } else{
            System.out.println("Deselected");
        }
        
    }
}
