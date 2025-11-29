package com.comp2042.ui.systems.managers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Manages game state (paused, game over). <br>
 * EXTRACTED FROM: GuiController <br>
 * SOLID principle applied: Single Responsibility Principle <br>
 * - Only manages game state, nothing else
 */
public class GameStateProperty {
    private final BooleanProperty isPaused = new SimpleBooleanProperty(false);
    private final BooleanProperty isGameOver = new SimpleBooleanProperty(false);

    public void setPaused(boolean paused) {
        isPaused.setValue(paused);
    }

    public void setGameOver(boolean gameOver) {
        isGameOver.setValue(gameOver);
    }

    public boolean isPaused() {
        return isPaused.getValue();
    }

    public boolean isGameOver() {
        return isGameOver.getValue();
    }

    public BooleanProperty pausedProperty() {
        return isPaused;
    }

    public BooleanProperty gameOverProperty() {
        return isGameOver;
    }

    /**
     * To determine if program should be taking in input.<br>
     * Used by InputHandler.
     * @return TRUE (allow input) if game is not paused and not over
     */
    public boolean canProcessInput() {
        return !isPaused() && !isGameOver();
    }

    /**
     * For new game conditions.
     */
    public void reset() {
        isPaused.setValue(false);
        isGameOver.setValue(false);
    }
}