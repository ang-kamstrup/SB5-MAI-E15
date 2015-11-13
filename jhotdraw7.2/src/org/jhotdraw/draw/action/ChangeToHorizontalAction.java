/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw.action;

import java.awt.event.ActionEvent;
import java.util.Locale;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author spider
 */
public class ChangeToHorizontalAction extends AbstractSelectedAction{

    private ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels", Locale.getDefault());
    
    public ChangeToHorizontalAction(DrawingEditor editor) {
        super(editor);
        labels.configureAction(this, "edit.changeToHorizontal");
        setEnabled(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        System.out.println("Horizontal Button action performed!");
        changeToHorizontal();
        setEnabled(true);
    }
    
    public void changeToHorizontal(){
        
    }
    
    
    
}
