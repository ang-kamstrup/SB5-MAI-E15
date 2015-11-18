import javax.swing.JToggleButton;
import junit.framework.Assert;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.action.ButtonFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jesper_wohlert
 */
public class ZoomSelectionTest {
    
    private JToggleButton button;
    private DrawingView view;
    
    public ZoomSelectionTest() {
        view = new DefaultDrawingView();
        button = ButtonFactory.createZoomSelectionButton(view);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void test() {
        button.doClick();
        double factor = view.getScaleFactor();
        
        
        
        Assert.assertNotSame(factor, factor);
    }
}
