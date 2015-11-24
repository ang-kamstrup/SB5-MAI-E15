/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.filters;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Marti13/MartinBagge
 */
public class BlackHole {
    
    public BufferedImage createFilter(BufferedImage originalImage) {
        BufferedImage blackHoleImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), originalImage.getType());

        Color color = Color.BLACK;
        int blackPixel = color.getRGB();

        for (int y = 0; y < originalImage.getHeight(); y++) {
            for (int x = 0; x < originalImage.getWidth(); x++) {

                if (pixelNumber(x,y,0.35f,0.65f, originalImage)) {
                    blackHoleImage.setRGB(x, y, blackPixel);

                } else if(pixelNumber(x,y,0.30f,0.70f, originalImage)){
                    color = new Color(originalImage.getRGB(x, y));
                    color = convertColor(color, 25);
                    blackHoleImage.setRGB(x, y, color.getRGB());
                }else if (pixelNumber(x,y,0.25f,0.75f, originalImage)) {
                    color = new Color(originalImage.getRGB(x, y));
                    color = convertColor(color, 50);
                    blackHoleImage.setRGB(x, y, color.getRGB());
                } else if(pixelNumber(x,y,0.20f,0.80f, originalImage)){
                    color = new Color(originalImage.getRGB(x, y));
                    color = convertColor(color, 75);
                    blackHoleImage.setRGB(x, y, color.getRGB());
                }else if (pixelNumber(x,y,0.15f,0.85f, originalImage)) {
                    color = new Color(originalImage.getRGB(x, y));
                    color = convertColor(color, 100);
                    blackHoleImage.setRGB(x, y, color.getRGB());
                } else if(pixelNumber(x,y,0.10f,0.90f, originalImage)){
                    color = new Color(originalImage.getRGB(x, y));
                    color = convertColor(color, 125);
                    blackHoleImage.setRGB(x, y, color.getRGB());
                }else if(pixelNumber(x,y,0.05f,0.95f, originalImage)){
                    color = new Color(originalImage.getRGB(x, y));
                    color = convertColor(color, 150);
                    blackHoleImage.setRGB(x, y, color.getRGB());
                }else{
                    blackHoleImage.setRGB(x, y, originalImage.getRGB(x, y));
                }

            }
        }
        return blackHoleImage;

    }

    private Color convertColor(Color color, int increase) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        int newRed = 0;
        int newGreen = 0;
        int newBlue = 0;

        newRed += increase;
        if (newRed > red) {
            newRed = red;
        }

        newGreen += increase;
        if (newGreen > green) {
            newGreen = green;
        }

        newBlue += increase;
        if (newBlue > blue) {
            newBlue = blue;
        }


        Color newColor = new Color(newRed, newGreen, newBlue);
        return newColor;
    }
    
    private boolean pixelNumber(int x, int y, float percentageLow, float percentageHigh, BufferedImage originalImage){
        if(x > originalImage.getWidth() * percentageLow && x < originalImage.getWidth() * percentageHigh
                        && y > originalImage.getHeight() * percentageLow && y < originalImage.getHeight() * percentageHigh){
            return true;
        }
        return false;
    }
    
}
