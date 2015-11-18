package org.jhotdraw.samples.svg.action;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.prefs.Preferences;
import org.jhotdraw.app.*;
import org.jhotdraw.app.action.*;
import javax.swing.*;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.samples.svg.*;
import org.jhotdraw.samples.svg.io.*;
import org.jhotdraw.util.ResourceBundleUtil;
import org.jhotdraw.util.prefs.PreferencesUtil;

/**
 * ViewSourceAction.
 *
 * @author Werner Randelshofer
 * @version 1.1 2009-04-10 Reuse dialog window. <br>1.0 19. Mai 2007 Created.
 */
public class ViewLayers extends AbstractViewAction {

    static JDialog dialog;
    static DrawingView view;
    
    public final static String ID = "view.viewLayers";
    /**
     * We store the dialog as a client property in the view.
     */
    private final static String DIALOG_CLIENT_PROPERTY = "view.viewLayers.dialog";

    /**
     * Creates a new instance.
     */
    public ViewLayers(Application app) {
        super(app);
        //view = app.getActiveView();
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        labels.configureAction(this, ID);
    }

    @FeatureEntryPoint(JHotDrawFeatures.VIEW_LAYERS)
    public void actionPerformed(ActionEvent e) {
        final SVGView p = (SVGView) getActiveView();
        SVGOutputFormat format = new SVGOutputFormat();
        format.setPrettyPrint(true);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();

        try {
            format.write(buf, p.getDrawing());
            String source = buf.toString("UTF-8");

            
            if (p.getClientProperty(DIALOG_CLIENT_PROPERTY) == null) {
                dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(p.getComponent()));
                p.putClientProperty(DIALOG_CLIENT_PROPERTY, dialog);
                dialog.setTitle("Layers");
                dialog.setResizable(true);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                
                
                //view = p.getEditor().
                
                view = p.getEditor().getActiveView();
                
                
                ListModeller lm = new ListModeller(view);
                dialog.getContentPane().add(lm);
                
                
                dialog.setSize(600, 300);               //set later!!!
                dialog.setLocationByPlatform(true);
            } else {
                dialog = (JDialog) p.getClientProperty(DIALOG_CLIENT_PROPERTY);
                JTextArea ta = (JTextArea) ((JScrollPane) dialog.getContentPane().getComponent(0)).getViewport().getView();
                ta.setText(source);
            }

            Preferences prefs = Preferences.userNodeForPackage(getClass());
            PreferencesUtil.installFramePrefsHandler(prefs, "viewLayers", dialog);

            dialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent evt) {
                    getApplication().removeWindow(dialog);
                    p.putClientProperty(DIALOG_CLIENT_PROPERTY, null);
                }
            });

            getApplication().addWindow(dialog, getActiveView());
            dialog.setVisible(true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
