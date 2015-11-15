package org.jhotdraw.color;

import java.awt.Color;
import java.util.*;
import org.jhotdraw.draw.action.ColorIcon;

/**
 *
 * @author Kap
 */
public class FavoriteColors extends Observable {

    //Singleton pattern
    private static FavoriteColors INSTANCE = null;

    private FavoriteColors() {
    }

    public static FavoriteColors getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FavoriteColors();
            init();
        }
        return INSTANCE;
    }
    //Observer pattern    
    private java.util.List<Observer> observers = new ArrayList<Observer>();
    private int state;
    //Class members
    private static java.util.List<ColorIcon> FAVOURITE_COLORS;
    private static int FAVOURITE_COLORS_COLUMN_COUNT = 12;

    /**
     * Initialize the favourite colors with a blank slate of 50 white colors.
     */
    private static void init() {
        LinkedList<ColorIcon> m = new LinkedList<ColorIcon>();
        FAVOURITE_COLORS = m;
    }

    /**
     * Adds a favorite color and notifies the attached observers. Duplicate
     * colors are not added, but rather moved to the front
     *
     * @param color
     */
    public void addFavoriteColor(ColorIcon colorIcon) {
        if (isColorUnique(colorIcon)) {
            System.out.println("unique");
            FAVOURITE_COLORS.add(0, colorIcon);
        } else if (FAVOURITE_COLORS.size() > 1) {
            System.out.println("moved");
            moveToFront(colorIcon);
        } else {
            System.out.println("nothing");
            return;
        }
        setChanged();
        notifyObservers();
    }

    public void clearFavoriteColors() {
        this.FAVOURITE_COLORS.clear();
    }

    /**
     * Get all favorite colors.
     *
     * @return - list of all favourite colors.
     */
    public java.util.List<ColorIcon> getColors() {
        return FAVOURITE_COLORS;
    }

    /**
     * Get the column count, this determines how many columns are in the
     * favourite colors JPopupButton.
     *
     * @return
     */
    public int getColumnCount() {
        return FAVOURITE_COLORS_COLUMN_COUNT;
    }

    /**
     * Register an observer to this object.
     *
     * @param observer - The observer to observe this object.
     */
    public void registerObserver(Observer observer) {
        System.out.println("Registering an observer Count: " + observers.size());
        observers.add(observer);
    }

    /**
     * Checks if the color already exists in the FAVOURITE_COLORS
     *
     * @param colorIcon - The color to check for.
     * @return
     */
    public boolean isColorUnique(ColorIcon colorIcon) {
        Color color = colorIcon.getColor();
        for (ColorIcon ci : FAVOURITE_COLORS) {
            if (color.equals(ci.getColor())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Moves specific color to the front of the list.
     *
     * @param colorIcon
     */
    public void moveToFront(ColorIcon colorIcon) {
        int index = 0;
        Color color = colorIcon.getColor();
        for (ColorIcon ci : FAVOURITE_COLORS) {
            if (color.equals(ci.getColor())) {
                FAVOURITE_COLORS.remove(index);
                break;
            }
            index++;
        }
        FAVOURITE_COLORS.add(0, colorIcon);
    }

    /**
     * Unregisters an observer from this object.
     *
     * @param observer - The observer to no longer observe on this object.
     */
    public void unregisterObserver(Observer observer) {
        observers.remove(observer);
    }
}