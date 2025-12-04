package com.comp2042.ui.elements;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Manages game level progression.<br>
 * SOLID: Single Responsibility - Only manages level logic<br>
 * Design Pattern: Strategy - Fall speed calculation can be changed independently
 * Design Pattern: Observer - Uses JavaFX properties for automatic UI updates<br>
 */
public class LevelSystem {
    private static final int ROWS_PER_LEVEL = 5;
    private static final int MAX_LEVEL = 10;
    private static final int BASE_FALL_SPEED_MS = 600;
    private static final int MIN_FALL_SPEED_MS = 100;

    private final IntegerProperty currentLevel = new SimpleIntegerProperty(1);
    private final IntegerProperty totalRowsCleared = new SimpleIntegerProperty(0);

    public IntegerProperty currentLevelProperty() {
        return currentLevel;
    }

    public int getCurrentLevel() {
        return currentLevel.get();
    }

    public int getTotalRowsCleared() {
        return totalRowsCleared.get();
    }

    /**
     * Updates rows cleared and checks for level up
     * @param rowsCleared Number of rows just cleared
     * @return true if leveled up, false otherwise
     */
    public boolean addClearedRows(int rowsCleared) {
        int oldLevel = currentLevel.get();
        totalRowsCleared.set(totalRowsCleared.get() + rowsCleared);

        int newLevel = Math.min(
                1 + (totalRowsCleared.get() / ROWS_PER_LEVEL),
                MAX_LEVEL
        );

        if (newLevel > oldLevel) {
            currentLevel.set(newLevel);
            return true;
        }
        return false;
    }

    /**
     * Calculates fall speed based on current level
     */
    public int getFallSpeedMs() {
        int level = currentLevel.get();
//        Speed increases by 50ms per level
        int speedDecrease = (level - 1) * 50;
        return Math.max(MIN_FALL_SPEED_MS, BASE_FALL_SPEED_MS - speedDecrease);
    }

    /**
     * Reset level back to level 1
     */
    public void reset() {
        currentLevel.set(1);
        totalRowsCleared.set(0);
    }
}