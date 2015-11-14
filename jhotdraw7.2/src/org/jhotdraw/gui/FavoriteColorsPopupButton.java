package org.jhotdraw.gui;

import java.awt.Color;
import java.awt.Shape;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.jhotdraw.color.FavoriteColors;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.action.AttributeAction;
import org.jhotdraw.draw.action.ButtonFactory;
import org.jhotdraw.draw.action.ColorIcon;
import org.jhotdraw.draw.action.FavoriteColorAddAction;
import org.jhotdraw.draw.action.FavoriteColorIcon;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author Kap
 */
public class FavoriteColorsPopupButton extends JPopupButton implements Observer {

    private Map<AttributeKey, Object> defaultAttributes;
    private AttributeKey<Color> attributeKey;
    private DrawingEditor editor;
    private ResourceBundleUtil labels;
    private String labelKey;
    private Shape colorShape;
    private FavoriteColors favColors;
    
    /** Creates a new instance. */
    public FavoriteColorsPopupButton(
            DrawingEditor editor, AttributeKey<Color> attributeKey,
            String labelKey, ResourceBundleUtil labels,
            Map<AttributeKey, Object> defaultAttributes,
            Shape colorShape) {
        this.defaultAttributes = defaultAttributes;
        this.attributeKey = attributeKey;
        this.editor = editor;
        this.labels = labels;
        this.labelKey = labelKey;
        this.colorShape = colorShape;

        
        init();

    }

    /**
     * Adds color swatches(Buttons) to the JPopupButton.
     * @param swatches - The ColorIcons from FavoriteColors
     */
    private void addSwatches(java.util.List<ColorIcon> swatches) {
        //"Add to favorites" button
        ImageIcon favColorAddIcon = new ImageIcon(
                ButtonFactory.class.getResource("/org/jhotdraw/draw/action/images/attributeFavoriteColor.png"));
        Action a;
        add(
                a = new FavoriteColorAddAction(
                editor,
                attributeKey,
                labels.getToolTipTextProperty("attribute.color.colorChooser"),
                favColorAddIcon,
                defaultAttributes));
        a.putValue(Action.SHORT_DESCRIPTION, labels.getToolTipTextProperty("attribute.color.favoriteColor"));
        
        //Favorite colors
        for (ColorIcon swatch : swatches) {
            AttributeAction aa;
            HashMap<AttributeKey, Object> attributes = new HashMap<AttributeKey, Object>(defaultAttributes);
            attributes.put(attributeKey, swatch.getColor());
            this.add(aa =
                    new AttributeAction(
                    editor,
                    attributes,
                    labels.getToolTipTextProperty(labelKey),
                    swatch));
            aa.putValue(Action.SHORT_DESCRIPTION, swatch.getName());
        }

        Icon icon = new FavoriteColorIcon(editor,
                attributeKey,
                labels.getIconProperty(labelKey, ButtonFactory.class).getImage(),
                colorShape);

        setIcon(icon);
        setDisabledIcon(icon);
    }

    /**
     * Initializes the JPopupButton - 
     * Registers it as an Observer on FavoriteColors.
     */
    private void init() {
        FavoriteColors.getInstance().addObserver(this);
        setPopupAlpha(1f);

        if (defaultAttributes == null) {
            defaultAttributes = new HashMap<AttributeKey, Object>();
        }

        labels.configureToolBarButton(this, labelKey);
        setColumnCount(FavoriteColors.getInstance().getColumnCount(), false);

        //Swatches
        addSwatches(FavoriteColors.getInstance().getColors());
        setFocusable(false);
    }

    /**
     * Updates when a change has occurred in FavoriteColors.
     * @param o
     * @param arg 
     */
    public void update(Observable o, Object arg) {
        this.removeAllComponents();
        addSwatches(FavoriteColors.getInstance().getColors());
    }
}