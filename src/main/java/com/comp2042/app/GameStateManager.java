package com.comp2042.app;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Manages game state (paused, game over). <br>
 * EXTRACTED FROM: GuiController <br>
 * SOLID principle applied: Single Responsibility Principle <br>
 * - Only manages game state, nothing else
 */
public class GameStateManager {
    private final BooleanProperty isPaused = new SimpleBooleanProperty(false);
    private final BooleanProperty isGameOver = new SimpleBooleanProperty(false);

    public boolean isPaused() {
        return isPaused.getValue();
    }

    public void setPaused(boolean paused) {
        isPaused.setValue(paused);
    }

    public BooleanProperty pausedProperty() {
        return isPaused;
    }

    public boolean isGameOver() {
        return isGameOver.getValue();
    }

    public void setGameOver(boolean gameOver) {
        isGameOver.setValue(gameOver);
    }

    public BooleanProperty gameOverProperty() {
        return isGameOver;
    }

    public boolean canProcessInput() {
        return !isPaused() && !isGameOver();
    }

    public void reset() {
        isPaused.setValue(false);
        isGameOver.setValue(false);
    }
}