/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.revisionhistory.RevisionController;

/**
 *
 * @author emilfrisk
 */
public class SVGApplication extends DefaultSDIApplication {
    
    static RevisionController revisionController;
    
    public SVGApplication(){
    }

    @Override
    public void init() {
        super.init();
        revisionController = new RevisionController();
    }
    
    public void revisionHistory(){
        new Thread(new Runnable() {

            public void run() {
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException ex) {
                    //TODO: gør noget ved denne
                }
                SVGView svgView  = (SVGView) getActiveView();
                revisionController.saveRevision(svgView.getDrawing());
            }
        }).start();
    }
    
}
