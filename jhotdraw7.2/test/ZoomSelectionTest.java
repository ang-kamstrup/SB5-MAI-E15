import javax.swing.JToggleButton;
import junit.framework.Assert;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.SelectionTool;
import org.jhotdraw.draw.ZoomSelectionTool;
import org.jhotdraw.draw.action.ButtonFactory;
import org.jhotdraw.draw.action.ZoomSelectionAction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

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
        view.getEditor();
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
    public void testToolSelection() {
        button.doClick();        
        Assert.assertTrue(view.getEditor().getTool() instanceof ZoomSelectionTool);
    }
}
