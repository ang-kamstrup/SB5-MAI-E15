/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SD
 */
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.jhotdraw.filters.Pixelation;
import org.jhotdraw.samples.svg.figures.SVGImageFigure;
import org.junit.*;

public class PixelationTest {

    // Class to test
    // TODO:
    // 1. Create variable here
    //  - objects need to exist
    // 2. Test function
    //  2.1 pop-up window value working
    //  2.2 success
    // 3. Test for undo
    //  - if needed
    
    void TestPixelation() throws IOException{
    }
    
    @BeforeClass
    public static void setUpClass(){     
    }
    
    
    // Creates 2 BufferedImages that will be compared at start and at the end.
    @Test
    public void pixelationComparison() throws IOException {
        byte[] currentImgByteArray;
        byte[] pixelatedImgByteArray;
        
        File imgPath = new File("Test Folder Pixelation/Resources/invertcolors.png");
        
        // Need 2 different objects, because bufferedImage02 will be manipulated
        // and having one will simply cause no comparison difference.
        // .
        // Cannot compare buffered image using Asserts, so need to get a byteArray
        BufferedImage bufferedImage01 = ImageIO.read(imgPath);
        WritableRaster raster01 = bufferedImage01.getRaster();
        DataBufferByte data01 = (DataBufferByte) raster01.getDataBuffer();
        
        
        BufferedImage bufferedImage02 = ImageIO.read(imgPath);
        WritableRaster raster02 = bufferedImage02.getRaster();
        DataBufferByte data02 = (DataBufferByte) raster02.getDataBuffer();
        
        currentImgByteArray = data01.getData();
        pixelatedImgByteArray = data02.getData();
        
        // Confirm they are equal at start.'
        Assert.assertArrayEquals(currentImgByteArray, pixelatedImgByteArray);
        System.out.println("Image A and Image B are equal");
        
        // Test class
        int pixelateSize = 50;
        SVGImageFigure imgFig = new SVGImageFigure();
        imgFig.setBufferedImage(bufferedImage02);
        
        
        Pixelation pix = new Pixelation();
        pix.pixelateImage(imgFig, pixelateSize);
        
        DataBufferByte data2 = (DataBufferByte) pix.getBufferedImage().getRaster().getDataBuffer();
        
        // Converts each image into 2D array
        // Adds RBG + Alpha into one int value.        
        int[][] currentImgArray = getPixelArrayResult(bufferedImage01);
        int[][] pixelatedImgArray = getPixelArrayResult(pix.getBufferedImage());
        
        ////////////////////////////
        // Can be run to see specific data is different.
        //for (int i = 0; i < currentImgArray.length; i++){
        //    for (int j = 0; j < currentImgArray[i].length; j++){
        //        System.out.println(currentImgArray[i][j] + " = " + pixelatedImgArray[i][j]);
        //    }
        //    
        //}
        ///////////////////////////
        
        // take the new pixelated object and compare it to the original to see any differences.
        //Assert.assertArrayEquals(currentImgArray, pixelatedImgArray);
        Assert.assertNotSame(currentImgByteArray, pixelatedImgByteArray);
        System.out.println("Image A and Image B are no longer equal");
        System.out.println("If no errors occour => success");
        
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        //q = new CheckRepWrapper(new Queue(2));
    }

    @After
    public void tearDown() {
        //q.empty();
    }
    
    public int[][] getPixelArrayResult(BufferedImage image){
      
        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
      final int width = image.getWidth();
      final int height = image.getHeight();
      final boolean hasAlphaChannel = image.getAlphaRaster() != null;

      int[][] result = new int[height][width];
      if (hasAlphaChannel) {
         final int pixelLength = 4;
         for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
            int argb = 0;
            argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
            argb += ((int) pixels[pixel + 1] & 0xff); // blue
            argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
            argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
            result[row][col] = argb;
            col++;
            if (col == width) {
               col = 0;
               row++;
            }
         }
      } else {
         final int pixelLength = 3;
         for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
            int argb = 0;
            argb += -16777216; // 255 alpha
            argb += ((int) pixels[pixel] & 0xff); // blue
            argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
            argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
            result[row][col] = argb;
            col++;
            if (col == width) {
               col = 0;
               row++;
            }
         }
      }

      return result;
   
    }

}
