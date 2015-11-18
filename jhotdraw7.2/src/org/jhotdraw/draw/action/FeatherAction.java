/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw.action;

import java.util.Locale;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.util.ResourceBundleUtil;
import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import org.jhotdraw.undo.*;
import org.jhotdraw.util.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import org.jhotdraw.app.JHotDrawFeatures;
import org.jhotdraw.draw.*;
import org.jhotdraw.geom.*;
import static org.jhotdraw.draw.AttributeKeys.*;

/**
 *
 * @author ChristianArentsen
 */
public class FeatherAction extends AbstractDrawingViewAction {
    
     private ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels", Locale.getDefault());
     private DrawingView view = getView();
     
public FeatherAction(DrawingView view) {
        super(view);
        labels.configureAction(this, "edit.feather");
        setEnabled(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(!view.getSelectedFigures().isEmpty()){
            
        }
        else{
            
        }
    }
}

