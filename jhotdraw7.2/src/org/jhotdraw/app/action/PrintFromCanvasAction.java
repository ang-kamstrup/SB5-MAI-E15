/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.util.*;

/**
 *
 * @author Jesso12
 */
public class PrintFromCanvasAction extends AbstractAction implements IAreaObserver {

    private final static String ID = "edit.printFromCanvas";
    private JFrame helperFrame = new JFrame("Print from canvas");
    private JLabel helperLabel = new JLabel("Hold the left mouse button down and select what you need to print");
    private Robot robot;
    private Rectangle rectangle;
    /**
     * Flag if the user has chosen to print from the canvas.
     */
    private static boolean readyToPrint;
    /**
     * Image to print.
     */
    private BufferedImage image;
    private Print print;

    public PrintFromCanvasAction() {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        labels.configureAction(this, ID);
    }

    public void actionPerformed(ActionEvent ae) {
        readyToPrint = true;
        //Implement print what is selected here.
        openHelperFrame();
    }

    /**
     * To see if print is not null for unit testing.
     *
     * @return print reference
     */
    public Print getPrint() {
        return print;
    }

    /**
     * To see the image is not null for unit testing.
     *
     * @return image reference
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Open up a JFrame for displaying how to print the selected area from the
     * canvas.
     */
    private void openHelperFrame() {
        helperFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        helperFrame.add(helperLabel, BorderLayout.CENTER);
        helperFrame.pack();
        helperFrame.setLocationRelativeTo(null);
        helperFrame.setVisible(true);
    }

    /**
     * Create an bufferedImage from what selected.
     * @param drawing view
     */
    private void createImageFromScreenCapture(DrawingView view) {
        if (readyToPrint && rectangle != null) {
            try {
                //Get the selected area an take a screenshot of that.
               
                JComponent comp;
                Point p;
                if (view != null) {
                    comp = view.getComponent();
                    p = comp.getLocationOnScreen();
                } else {
                    p = new Point(100, 100);
                }

                Dimension di = rectangle.getSize();
                Point pi = rectangle.getLocation();

                int newX = p.x + pi.x;
                int newY = p.y + pi.y;

                Point pio = new Point(newX, newY);

                Rectangle rect = new Rectangle(pio, di);

                robot = new Robot();
                image = robot.createScreenCapture(rect);

            } catch (AWTException ex) {
                Logger.getLogger(PrintFromCanvasAction.class.getName()).log(Level.SEVERE, null, ex);
                throw new Error("Unable to init screen capture tool");
            }
            readyToPrint = false;

            helperFrame.setVisible(false);
            helperFrame.dispose();
            fetchScreenToPrint(image);
        }
    }

    /**
     * Forward the bufferedimage to printer.
     * @param image
     */
    private void fetchScreenToPrint(BufferedImage image) {
        if (image != null) {
            print = new Print(image);
        }
    }

    /**
     * Part of strategy pattern.
     * @param rectangle
     * @param view
     */
    public void areaToPrint(Rectangle rectangle, DrawingView view) {
        this.rectangle = rectangle;
        if (readyToPrint) {
            createImageFromScreenCapture(view);
        }
    }
}
