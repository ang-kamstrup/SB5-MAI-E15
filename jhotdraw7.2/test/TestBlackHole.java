
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.aspectj.lang.annotation.After;
import org.jhotdraw.filters.BlackHole;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Marti13/MartinBagge
 */
public class TestBlackHole {

    BufferedImage testImage, blackHoleTestImage;
    byte[] originalImagePixels, blackHoleImagePixels;
    
    public TestBlackHole() {
    }

    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws IOException {
        //Setup Pixel arrays because Assert cannot compare two images
        testImage = ImageIO.read(new File("test/resources/testImage.jpg"));
        originalImagePixels = ((DataBufferByte) testImage.getRaster().getDataBuffer()).getData();
        blackHoleImagePixels = originalImagePixels;
        //Assert the arrays
        Assert.assertArrayEquals(originalImagePixels, blackHoleImagePixels);
        System.out.println("The two pixel arrays are the same!");
    }
     
    @After
    public void tearDown() {
    }

    @Test
    public void testBlackHole(){
        //Run the test image through the filter and change one of the pixel arrays
        BlackHole blackHole = new BlackHole();
        blackHoleTestImage = blackHole.createFilter(testImage);
        blackHoleImagePixels = ((DataBufferByte) blackHoleTestImage.getRaster().getDataBuffer()).getData();

        //Assert the arrays which are now different
        Assert.assertNotSame(originalImagePixels, blackHoleImagePixels);
        System.out.println("The two pixel arrays are now different!");
        System.out.println("Test succesful");
    }
}
