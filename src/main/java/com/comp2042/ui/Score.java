package com.comp2042.ui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * An object of this class is created to hold the overall score of the player.
 */
public final class Score {

    private final IntegerProperty score = new SimpleIntegerProperty(0);

    /**
     * @return  Score number.
     */
    public IntegerProperty scoreProperty() {
        return score;
    }

    /**
     * Adds points obtained to player's overall score.
     * @param i The amount of points the player obtained from clearing a number of rows.
     */
    public void add(int i){
        score.setValue(score.getValue() + i);
    }

    /**
     * Sets overall score value to 0.
     */
    public void reset() {
        score.setValue(0);
    }
}
