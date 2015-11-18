
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.DataBufferByte;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.action.EdgeDetectionAction;
import org.jhotdraw.samples.svg.figures.SVGImageFigure;
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
public class EdgeDetectionBetterTest {
    BufferedImage originalImage;
    BufferedImage convertedImage;
    BufferedImageOp imgOp;
    byte[] originalImagePixels;
    byte[] convertedImagePixels;
    SVGImageFigure imgFig;
    EdgeDetectionAction eda;
    LinkedList<Figure> figures;

    public EdgeDetectionBetterTest() {

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
        imgFig = new SVGImageFigure();
        imgFig.setBufferedImage(originalImage);
        
        eda = new EdgeDetectionAction(null);
        figures = new LinkedList<Figure>();
        figures.add(imgFig);
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
        
        //do edge detection
        eda.edgeDetection(figures);
        convertedImagePixels = ((DataBufferByte) convertedImage.getRaster().getDataBuffer()).getData();
        byte[] edPixels = ((DataBufferByte) imgFig.getBufferedImage().getRaster().getDataBuffer()).getData();
        boolean sameImage = Arrays.equals(edPixels, convertedImagePixels);
        assertTrue("Something wrong with edge detection change field (first edge detection)", eda.getChanged());
        assertTrue("Problems with first edge detection", sameImage);
        
        //do undo
        eda.doUndo(figures);
        byte[] undoPix = ((DataBufferByte) imgFig.getBufferedImage().getRaster().getDataBuffer()).getData();
        boolean sameUndoImage = Arrays.equals(undoPix, originalImagePixels);
        assertTrue("Something wrong with edge detection change field (first undo)", eda.getChanged());
        assertTrue("Problems with first undo", sameUndoImage);
        
        //do undo again, nothing should change
        eda.doUndo(figures);
        byte[] undoPix2 = ((DataBufferByte) imgFig.getBufferedImage().getRaster().getDataBuffer()).getData();
        boolean sameUndoImage2 = Arrays.equals(undoPix2, originalImagePixels);
        assertTrue("Problems with second undo", sameUndoImage2);
        
        //do edge detection again, should work normally like in first call of edgeDetection
        eda.edgeDetection(figures);
        byte[] edPixels2 = ((DataBufferByte) imgFig.getBufferedImage().getRaster().getDataBuffer()).getData();
        boolean sameImage2 = Arrays.equals(edPixels2, convertedImagePixels);
        assertTrue("Problems with second edge detection", sameImage2);
        
        LinkedList<Figure> emptyList = new LinkedList<Figure>();
        eda.edgeDetection(emptyList);
        assertFalse("Something wrong when empty list is sent to edgeDetection", eda.getChanged());

    }
}