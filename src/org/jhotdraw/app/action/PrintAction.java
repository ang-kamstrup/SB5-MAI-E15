/*
 * @(#)PrintAction.java  2.0  2007-07-31
 *
 * Copyright (c) 1996-2007 by the original authors of JHotDraw
 * and all its contributors.
 * All rights reserved.
 *
 * The copyright of this software is owned by the authors and  
 * contributors of the JHotDraw project ("the copyright holders").  
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * the copyright holders. For details see accompanying license terms. 
 */
package org.jhotdraw.app.action;

import java.awt.event.*;
import javax.swing.*;
import org.jhotdraw.app.*;
import org.jhotdraw.util.*;

/**
 * Presents a printer dialog to the user and then prints the View to the
 * chosen printer.
 * <p>
 * This action requires that the view implements the PrintableView
 * interface.
 * <pre>
 * public Pageable createPageable();
 * </pre>
 * <p>
 *
 * @author Werner Randelshofer
 * @version 2.0 2007-07-31 Rewritten to use an interface instead of
 * relying on Java Reflection. 
 * <br>1.0 January 1, 2007 Created.
 */
public class PrintAction extends AbstractViewAction {

    public final static String ID = "file.print";
    private Print print;

    /** Creates a new instance. */
    public PrintAction(Application app) {
        super(app);
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        labels.configureAction(this, ID);
    }

    //Init view and determine if which OS running and set enabling to true.
    public void actionPerformed(ActionEvent evt) {
        View view = getActiveView();
        print = new Print(view);
    }

    /**
     * Returns true if the action is enabled.
     * The enabled state of the action depends on the state that has been set
     * using setEnabled() and on the enabled state of the application.
     *
     * @return true if the action is enabled, false otherwise
     * @see Action#isEnabled
     */
    @Override public boolean isEnabled() {        
        return super.isEnabled() && (getActiveView() instanceof PrintableView);
    }
    
}
