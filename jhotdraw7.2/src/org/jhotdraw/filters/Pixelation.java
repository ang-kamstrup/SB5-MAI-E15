/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.filters;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import org.jhotdraw.samples.svg.figures.SVGImageFigure;

/**
 *
 * @author SD
 */
public class Pixelation {
    
    private BufferedImage bi;
    
    void Pixelation(){
        
    }
    
    public void pixelateImage(SVGImageFigure img, int PIX_SIZE){
        
        // Author bchociej from Stackoverflow
        
        bi = img.getBufferedImage();
        
        // Get the raster data (array of pixels)
        Raster src = bi.getData();

        // Create an identically-sized output raster
        WritableRaster dest = src.createCompatibleWritableRaster();
        // Loop through every PIX_SIZE pixels, in both x and y directions
        for(int y = 0; y < src.getHeight(); y += PIX_SIZE) {
            for(int x = 0; x < src.getWidth(); x += PIX_SIZE) {

            // Copy the pixel
            double[] pixel = new double[3];
            pixel = src.getPixel(x, y, pixel);

            // "Paste" the pixel onto the surrounding PIX_SIZE by PIX_SIZE neighbors
            // Also make sure that our loop never goes outside the bounds of the image
            for(int yd = y; (yd < y + PIX_SIZE) && (yd < dest.getHeight()); yd++) {
                for(int xd = x; (xd < x + PIX_SIZE) && (xd < dest.getWidth()); xd++) {
                    dest.setPixel(xd, yd, pixel);
                }
            }
            }
        }
        

// Save the raster back to the Image
    bi.setData(dest);
    }
    
    
    public BufferedImage getBufferedImage(){
        return bi;
    }
    
}
