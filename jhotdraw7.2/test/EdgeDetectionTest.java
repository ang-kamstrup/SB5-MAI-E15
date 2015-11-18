
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.DataBufferByte;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author klancar
 */
public class EdgeDetectionTest {
    BufferedImage originalImage;
    BufferedImage convertedImage;
    BufferedImageOp imgOp;
    byte[] originalImagePixels;
    byte[] convertedImagePixels;

    public EdgeDetectionTest() {

    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {

    }

    @Before
    public void setUp() throws IOException {
        originalImage = ImageIO.read(new File("test/resources/testImage.jpg"));
        float[] kernel = { 0.0f, -1.0f, 0.0f,
                            -1.0f, 4.0f, -1.0f,
                            0.0f, -1.0f, 0.0f};
        imgOp = new ConvolveOp(new Kernel(3, 3, kernel));
        originalImagePixels = ((DataBufferByte) originalImage.getRaster().getDataBuffer()).getData();
    }

    @After
    public void tearDown() {
      
    }

    @Test
    public void testEdgeDetection(){
        // TEST
        convertedImage = imgOp.filter(originalImage, null);
        convertedImagePixels = ((DataBufferByte) convertedImage.getRaster().getDataBuffer()).getData();
        for (int x = 0; x < convertedImage.getWidth(); x++) {
            for (int y = 0; y < convertedImage.getHeight(); y++)
            {
                int rgb = convertedImage.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = (rgb & 0xFF);

                int corrected = (Integer.parseInt("FF", 16) << 24) + (r << 16) + (g << 8) + b; 
                convertedImage.setRGB(x, y, corrected);
            }
        }
        // ASSERT
        boolean isImagePixelsSame = Arrays.equals(originalImagePixels, convertedImagePixels);
        assertFalse("something's wrong!", isImagePixelsSame);
    }
}