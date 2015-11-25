import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * TODO: Please add your UnitTest to this TestSuite using
 *
 * @Suite.SuiteClasses({YourUnitTest.class, OtherUnitTest.class})
 *
 * @author jcs
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({GroupingTest.class,
    AddPointTest.class,
    EdgeDetectionBetterTest.class,
    EdgeDetectionTest.class,
    FavoriteColorTest.class,
    GradientTest.class,
    GroupingTest.class,
    PixelationTest.class,
    PrintFromCanvasActionTest.class,
    SVGStarFigureTest.class,
    StarFigureTest.class,
    TestBlackHole.class,
    TestBlurOperation.class,
    ZoomSelectionTest.class,
    TestBlackHole.class
    })

public class JHotDrawTestSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

}
