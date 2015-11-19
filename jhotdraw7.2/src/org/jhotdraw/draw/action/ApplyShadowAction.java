/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw.action;

import java.awt.event.ActionEvent;
import java.util.Locale;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author S
 */
public class ApplyShadowAction extends AbstractSelectedAction{

    private ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.labels", Locale.getDefault());
    
    public ApplyShadowAction(DrawingEditor editor){
        super(editor);
        labels.configureAction(this, "edit.applyShadow");
        setEnabled(true);
    }
    public void actionPerformed(ActionEvent e) {
       
        getView().getEditor().getActiveView().clearSelection();
        //if(class.shadowField > 0 && class.shadowField <= 100){
        //SHADOW_OPACITY = (Double)shadowField/100;
    }            
    
    
}