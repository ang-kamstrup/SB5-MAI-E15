package org.jhotdraw.filters;

import java.awt.image.Kernel;
import org.jhotdraw.draw.Drawing;

public class GaussianBlurAlgorithm {
    private final int STANDARD_DEVIATION = 5;
    private int sigma = STANDARD_DEVIATION;
    private int imageHeight;
    private int imageWidth;
    float[] kernel;
    private int kernelRadius = 5;
    /*float[] matrix = {
    1/16f, 1/8f, 1/16f, 
    1/8f, 1/4f, 1/8f, 
    1/16f, 1/8f, 1/16f, 
    };*/
    
    //http://blog.ivank.net/fastest-gaussian-blur.html
    //http://groups.inf.ed.ac.uk/vision/GRASSIN/SkinSpotTool/skinSpotTool/GaussianSmooth.java
    //https://code.google.com/p/ptmviewer/source/browse/trunk/jpview/graphics/GaussianKernel.java?r=2
    //https://code.google.com/p/ptmviewer/source/browse/trunk/jpview/graphics/GaussianKernel.java?r=2
    
    public GaussianBlurAlgorithm(){
        
    }
    
    public GaussianBlurAlgorithm(Drawing drawing, int imageHeight, int imageWidth){
        this.imageHeight = imageHeight;
        this.imageWidth = imageWidth;
        kernel = createKernel();
    }
    
    private float[] createKernel() {
        kernel = new float[kernelRadius * kernelRadius]; //Creates a 5*5 matrix
        float sum = 0;
        
        for (int y = 0; y < kernelRadius; y++){
            for (int x = 0; x < kernelRadius; x++){
                int off = y * kernelRadius + x;
                int xx = x - kernelRadius / 2;
                int yy = y - kernelRadius / 2;
                kernel[off] = (float) Math.pow(
                        Math.E, -(xx * xx + yy * yy) / (2 * (sigma * sigma)));
                sum += kernel[off];
            }
        }
        
        for (int i = 0; i < kernel.length; i++)
            kernel[i] /= sum;
        
        return kernel;
    }
}