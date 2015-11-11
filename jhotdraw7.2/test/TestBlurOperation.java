import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import javax.imageio.ImageIO;
import org.jhotdraw.filters.GaussianKernel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dan/Kyzaq
 */
public class TestBlurOperation {
    BufferedImage testImage, blurredTestImage;
    BufferedImageOp blurOperation;
    byte[] expTestPixels, actTestBlurredPixels;
    
    public TestBlurOperation() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws IOException {
        testImage = ImageIO.read(new URL("http://www.joomlaworks.net/images/demos/galleries/abstract/7.jpg"));
        expTestPixels = ((DataBufferByte) testImage.getRaster().getDataBuffer()).getData();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testBlur(){
        
        // SETUP
        blurOperation = new ConvolveOp(GaussianKernel.createGaussianKernel());
        
        
        // TEST
        blurredTestImage = blurOperation.filter(testImage, null);
        actTestBlurredPixels = ((DataBufferByte) blurredTestImage.getRaster().getDataBuffer()).getData();

        // ASSERTS        
        //boolean isSameImage = expTestPixels.equals(actTestBlurredPixels);
        boolean isImagePixelsSame = Arrays.equals(expTestPixels, actTestBlurredPixels);
        assertFalse(isImagePixelsSame);
    }
}
