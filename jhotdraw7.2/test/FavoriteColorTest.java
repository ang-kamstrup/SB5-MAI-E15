
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.jhotdraw.color.FavoriteColors;
import org.jhotdraw.draw.action.ColorIcon;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kap
 */
public class FavoriteColorTest {

    Random rand = new Random();
    List testColors = new ArrayList();

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        //Makes sure FavoriteColors has been instaniated and the favorite colors cleared.
        FavoriteColors.getInstance().clearFavoriteColors();

        //Generate some random test colors
        FavoriteColors fc = FavoriteColors.getInstance();
        for (int i = 0; i < 10; i++) {
            int r = rand.nextInt(255);
            int g = rand.nextInt(255);
            int b = rand.nextInt(255);
            testColors.add(new ColorIcon(new Color(r, g, b)));
        }
    }

    @After
    public void tearDown() throws Exception {
        FavoriteColors.getInstance().clearFavoriteColors();
    }

    @Test
    public void testFavoriteColors() {
        //Add test colors to FavoriteColors
        for (Object colorIcon : testColors) {
            FavoriteColors.getInstance().addFavoriteColor((ColorIcon) colorIcon);
        }

        //Test that each added test color was correctly added to FavoriteColors.
        int i = 0;
        for (ColorIcon colorIcon : FavoriteColors.getInstance().getColors()) {
            boolean isSame = colorIcon.equals(testColors.get(i));
            assertTrue("Expected a different color", isSame);
            i++;
        }
        
        //Add a test color that was already previously added.
        //Make sure size stays the same (It should not be added)
        int sizePre = FavoriteColors.getInstance().getColors().size();
        FavoriteColors.getInstance().addFavoriteColor((ColorIcon)testColors.get(5));
        int result = (Integer.compare(sizePre, FavoriteColors.getInstance().getColors().size()));
        boolean isSame = result == 0 ? true: false;
        assertTrue("Duplicate color was added to FavoriteColors", isSame);
        
        //Make sure that the testColors.get(5) was moved to the front of FavoriteColors.
        boolean isMoved = FavoriteColors.getInstance().getColors().get(0).equals(testColors.get(5));
        assertTrue("Favorited color was not moved to the front of the list, when re-added as favorite", isMoved);

    }
}
