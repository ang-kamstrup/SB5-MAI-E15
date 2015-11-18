/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw.action;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import static org.jhotdraw.draw.AttributeKeys.*;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.gui.DrawingAttributeEditorHandler;
import org.jhotdraw.gui.JAttributeTextField;

/**
 *
 * @author frank
 */
public class CropAction extends AbstractSelectedAction{

    private DrawingEditor editor;
    private DefaultDrawingView view;
    private Collection<Figure> figures;
    private double x1 = 0;
    private double x2 = 0;
    private double y1 = 0;
    private double y2 = 0;
    
    public CropAction(DrawingEditor editor, DrawingView view) {
        super(editor);
        setEnabled(true);
        this.editor = editor;
        this.view = (DefaultDrawingView)view;
        
        figures = new ArrayList<Figure>();
    }

    public void actionPerformed(ActionEvent ae) {
        JButton button = (JButton) ae.getSource();
        int messageBoxButton = JOptionPane.YES_NO_OPTION;
        int messageBoxResult = JOptionPane.showConfirmDialog (null, "Would you like to crop the canvas?", "Crop tool", messageBoxButton);
        if(messageBoxResult == JOptionPane.YES_OPTION){
            int i = 0;
            for(Figure f : view.getDrawing().getChildren()){
                figures.add((Figure) f.clone());
                //For debugging
//                System.out.println("Figur " + i + ", x: " + f.getDrawingArea().x + ", y: " + f.getDrawingArea().y + ", h: " + f.getDrawingArea().height + ", w: " + f.getDrawingArea().width);
                if(i == 0){
                    x1 = f.getDrawingArea().x;
                    y1 = f.getDrawingArea().y;
                    x2 = f.getDrawingArea().width + x1;
                    y2 = f.getDrawingArea().height + y1;
                }
                if(f.getDrawingArea().x < x1){
                    x1 = f.getDrawingArea().x;
                }
                if(f.getDrawingArea().y < y1){
                    y1 = f.getDrawingArea().y;
                }
                if(f.getDrawingArea().width + f.getDrawingArea().x > x2){
                    x2 = f.getDrawingArea().width + f.getDrawingArea().x;
                }
                if(f.getDrawingArea().height + f.getDrawingArea().y > y2){
                    y2 = f.getDrawingArea().height + f.getDrawingArea().y;
                }
                i++;
            }
            AffineTransform transformer = new AffineTransform();
            transformer.translate(-x1, -y1);
            for(Figure f : view.getDrawing().getChildren()){
                f.transform(transformer);
            }
            //For debugging
//            System.out.println(x1);
//            System.out.println(y1);
//            System.out.println(x2);
//            System.out.println(y2);
            view.getDrawing().setAttribute(CANVAS_WIDTH, x2-x1);
            view.getDrawing().setAttribute(CANVAS_HEIGHT, y2-y1);
        }
    }
}
