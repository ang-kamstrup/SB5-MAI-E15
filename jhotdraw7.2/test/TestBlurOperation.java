import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
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
    byte[] unblurredImagePixels, blurredImagePixels;
    
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
        testImage = ImageIO.read(new File("test/resources/testImage.jpg"));
        unblurredImagePixels = ((DataBufferByte) testImage.getRaster().getDataBuffer()).getData();
        blurOperation = new ConvolveOp(GaussianKernel.createKernel());
    }
     
    @After
    public void tearDown() {
    }

    @Test
    public void testBlur(){
        // TEST
        blurredTestImage = blurOperation.filter(testImage, null);
        blurredImagePixels = ((DataBufferByte) blurredTestImage.getRaster().getDataBuffer()).getData();

        // ASSERT
        boolean isImagePixelsSame = Arrays.equals(unblurredImagePixels, blurredImagePixels);
        assertFalse(isImagePixelsSame);
    }
}
