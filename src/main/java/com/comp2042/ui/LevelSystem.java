package com.comp2042.ui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Manages game level progression.
 * SOLID: Single Responsibility - Only manages level logic
 * Design Pattern: Observer - Uses JavaFX properties for automatic UI updates
 */
public class LevelSystem {
    private static final int ROWS_PER_LEVEL = 5;
    private static final int MAX_LEVEL = 10;
    private static final int BASE_FALL_SPEED_MS = 400;
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
        System.out.println("LevelSystem hash: " + this);

        int oldLevel = currentLevel.get();
        totalRowsCleared.set(totalRowsCleared.get() + rowsCleared);

        int newLevel = Math.min(
                1 + (totalRowsCleared.get() / ROWS_PER_LEVEL),
                MAX_LEVEL
        );

        if (newLevel > oldLevel) {
            currentLevel.set(newLevel);
            System.out.println("New level");
            return true;
        }
        return false;
    }

    /**
     * Calculates fall speed based on current level
     * Design Pattern: Strategy - Fall speed calculation can be changed independently
     */
    public int getFallSpeedMs() {
        System.out.println("Speed increased");
        int level = currentLevel.get();
        // Speed increases by 30ms per level
        int speedDecrease = (level - 1) * 30;
        return Math.max(MIN_FALL_SPEED_MS, BASE_FALL_SPEED_MS - speedDecrease);
    }

    public void reset() {
        currentLevel.set(1);
        totalRowsCleared.set(0);
    }

//    public int getRowsUntilNextLevel() {
//        if (currentLevel.get() >= MAX_LEVEL) {
//            return 0; // Already at max level
//        }
//        int rowsForNextLevel = currentLevel.get() * ROWS_PER_LEVEL;
//        return rowsForNextLevel - totalRowsCleared.get();
//    }
}