package org.jhotdraw.filters;

import java.awt.image.Kernel;

/*
* Credits to this link for helping with the implementation 
*https://code.google.com/p/ptmviewer/source/browse/trunk/jpview/graphics/GaussianKernel.java?r=2
*/

public class GaussianKernel {
    
    public static Kernel createKernel(){
        Kernel kernel = new Kernel(5, 5, createGaussianKernel());
        return kernel;
    }
    
    private static float[] createGaussianKernel() {
        int STANDARD_DEVIATION = 5;
        int sigma = STANDARD_DEVIATION;
        int kernelRadius = 5;
        float[] gaussianKernel;
        gaussianKernel = new float[kernelRadius * kernelRadius];
        float sum = 0;
        
        sum = createKernelSum(kernelRadius, gaussianKernel, sigma, sum);
        fillKernel(gaussianKernel, sum);
        
        return gaussianKernel;
    }

    private static float createKernelSum(int kernelRadius, float[] gaussianKernel, int sigma, float sum) {
        for (int y = 0; y < kernelRadius; y++){
            for (int x = 0; x < kernelRadius; x++){
                int off = y * kernelRadius + x;
                int xx = x - kernelRadius / 2;
                int yy = y - kernelRadius / 2;
                gaussianKernel[off] = (float) Math.pow(
                        Math.E, -(xx * xx + yy * yy) / (2 * (sigma * sigma)));
                sum += gaussianKernel[off];
            }
        }
        return sum;
    }
    
    private static void fillKernel(float[] gaussianKernel, float sum) {
        for (int i = 0; i < gaussianKernel.length; i++) {
            gaussianKernel[i] /= sum;
        }
    }
}
