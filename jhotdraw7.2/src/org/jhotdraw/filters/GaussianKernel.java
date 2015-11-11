package org.jhotdraw.filters;

import java.awt.image.Kernel;

public class GaussianKernel {
    
    public static Kernel createGaussianKernel(){
        Kernel kernel = new Kernel(5, 5, createGaussianFloatKernel());
        return kernel;
    }
    
    private static float[] createGaussianFloatKernel() {
        int STANDARD_DEVIATION = 5;
        int sigma = STANDARD_DEVIATION;
        int kernelRadius = 5;
        float[] floatKernel;
        
        floatKernel = new float[kernelRadius * kernelRadius]; //Creates a 5*5 matrix
        float sum = 0;
        
        for (int y = 0; y < kernelRadius; y++){
            for (int x = 0; x < kernelRadius; x++){
                int off = y * kernelRadius + x;
                int xx = x - kernelRadius / 2;
                int yy = y - kernelRadius / 2;
                floatKernel[off] = (float) Math.pow(
                        Math.E, -(xx * xx + yy * yy) / (2 * (sigma * sigma)));
                sum += floatKernel[off];
            }
        }
        
        for (int i = 0; i < floatKernel.length; i++) {
            floatKernel[i] /= sum;
        }
        
        return floatKernel;
    }
}
