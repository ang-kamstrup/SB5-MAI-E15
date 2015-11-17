/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.JobAttributes;
import java.awt.PageAttributes;
import java.awt.PrintJob;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.PrinterResolution;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.jhotdraw.app.PrintableView;
import org.jhotdraw.app.View;
import org.jhotdraw.gui.JSheet;
import org.jhotdraw.gui.Worker;
import org.jhotdraw.util.Methods;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author Jesso12
 */
public class Print {
    private View view;
    private BufferedImage image;
    
    public Print(View view){
        this.view = view;
        
        view.setEnabled(false);
        getSystemProperty();
        view.setEnabled(true);
    }
    
    public Print(BufferedImage image){
        this.image = image;
        verifyImage(image);
    }
    
    private void getSystemProperty(){
         if (System.getProperty("apple.awt.graphics.UseQuartz", "false").equals("true")) {
            printQuartz();
        } else {
            printJava2D();
        }
    }
    
    //To avoid method constructor call.
    private void verifyImage(BufferedImage image){
        if(image != null){
          printBufferedImage(image);
        }
    }
    
    /*
     * This prints at 72 DPI only. We might need this for some JVM versions on
     * Mac OS X.*/
    public void printJava2D() {
        Pageable pageable = ((PrintableView) view).createPageable();
        if (pageable == null) {
            throw new InternalError("View does not have a method named java.awt.Pageable createPageable()");
        }

        try {
            PrinterJob job = PrinterJob.getPrinterJob();
            // FIXME - PrintRequestAttributeSet should be retrieved from View
            PrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
            attr.add(new PrinterResolution(300, 300, PrinterResolution.DPI));
            job.setPageable(pageable);
            if (job.printDialog()) {
                try {
                    job.print();
                } catch (PrinterException e) {
                    String message = (e.getMessage() == null) ? e.toString() : e.getMessage();
                    View view = this.view;
                    ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
                    JSheet.showMessageSheet(view.getComponent(),
                            "<html>" + UIManager.getString("OptionPane.css") +
                            "<b>" + labels.getString("couldntPrint") + "</b><br>" +
                            ((message == null) ? "" : message));
                }
            } else {
                System.out.println("JOB ABORTED!");
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    /*
     * This prints at 72 DPI only. We might need this for some JVM versions on
     * Mac OS X.*/

    public void printJava2DAlternative() {
        Pageable pageable = (Pageable) Methods.invokeGetter(view, "createPageable", null);
        if (pageable == null) {
            throw new InternalError("View does not have a method named java.awt.Pageable createPageable()");
        }

        try {
            final PrinterJob job = PrinterJob.getPrinterJob();
            PrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
            attr.add(new PrinterResolution(300, 300, PrinterResolution.DPI));
            job.setPageable(pageable);
            if (job.printDialog(attr)) {
                try {
                    job.print();
                } catch (PrinterException e) {
                    ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
                    JSheet.showMessageSheet(view.getComponent(),
                            labels.getFormatted("couldntPrint", e));
                }
            } else {
                System.out.println("JOB ABORTED!");
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * On Mac OS X with the Quartz rendering engine, the following code achieves
     * the best results.
     */
    public void printQuartz() {
        Frame frame = (Frame) SwingUtilities.getWindowAncestor(view.getComponent());
        final Pageable pageable = (Pageable) Methods.invokeGetter(view, "createPageable", null);
        final double resolution = 300d;
        JobAttributes jobAttr = new JobAttributes();
        // FIXME - PageAttributes should be retrieved from View
        PageAttributes pageAttr = new PageAttributes();
        pageAttr.setMedia(PageAttributes.MediaType.A4);
        pageAttr.setPrinterResolution((int) resolution);
        final PrintJob pj = frame.getToolkit().getPrintJob(
                frame,
                "Job Title",
                jobAttr,
                pageAttr);

        view.setEnabled(false);
        new Worker() {

            public Object construct() {

                // Compute page format from settings of the print job
                Paper paper = new Paper();
                paper.setSize(
                        pj.getPageDimension().width / resolution * 72d,
                        pj.getPageDimension().height / resolution * 72d);
                paper.setImageableArea(64d, 32d, paper.getWidth() - 96d, paper.getHeight() - 64);
                PageFormat pageFormat = new PageFormat();
                pageFormat.setPaper(paper);

                // Print the job
                try {
                    for (int i = 0,  n = pageable.getNumberOfPages(); i < n; i++) {
                        PageFormat pf = pageable.getPageFormat(i);
                        pf = pageFormat;
                        Graphics g = pj.getGraphics();
                        if (g instanceof Graphics2D) {
                            pageable.getPrintable(i).print(g, pf, i);
                        } else {
                            BufferedImage buf = new BufferedImage(
                                    (int) (pf.getImageableWidth() * resolution / 72d),
                                    (int) (pf.getImageableHeight() * resolution / 72d),
                                    BufferedImage.TYPE_INT_RGB);
                            Graphics2D bufG = buf.createGraphics();


                            bufG.setBackground(Color.WHITE);
                            bufG.fillRect(0, 0, buf.getWidth(), buf.getHeight());
                            bufG.scale(resolution / 72d, resolution / 72d);
                            bufG.translate(-pf.getImageableX(), -pf.getImageableY());
                            pageable.getPrintable(i).print(bufG, pf, i);
                            bufG.dispose();
                            g.drawImage(buf,
                                    (int) (pf.getImageableX() * resolution / 72d),
                                    (int) (pf.getImageableY() * resolution / 72d),
                                    null);
                            buf.flush();
                        }
                        g.dispose();
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                } finally {
                    pj.end();
                }
                return null;
            }

            public void finished(Object value) {
                view.setEnabled(true);
            }
        }.start();
    }
    
    public void printBufferedImage(BufferedImage imageToPrint){
        try {
          //create a blank printJob
          PrinterJob printJob = PrinterJob.getPrinterJob();
          printJob.setPrintable(new PrintImage(printJob, imageToPrint));

          if(printJob.printDialog()){
             printJob.print();
          }
          
     } catch (Exception e) {
          e.printStackTrace();
     } 
        
        
    }
}
