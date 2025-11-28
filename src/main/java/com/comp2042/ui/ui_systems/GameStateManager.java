package com.comp2042.ui.ui_systems;

import com.comp2042.input.event_controllers.InputEventListener;
import com.comp2042.ui.GameTimeLine;
import javafx.scene.layout.GridPane;

/**
 * Manages game state operations (new game, pause, resume, game over).<br>
 * SOLID: Single Responsibility - Only manages game state<br>
 * SOLID: Open/Closed - Easy to add new game state operations<br>
 * Design Pattern: Command - Each operation is a distinct command<br>
 */
public class GameStateManager {
    private final GameTimeLine gameTimeLine;
    private final GameStateProperty gameStateProperty;
    private final UILabelManager labelManager;
    private final InputEventListener eventListener;
    private final GridPane gamePanel;
    private final Runnable showSpecialShapeDisplay;

    public GameStateManager(
            GameTimeLine gameTimeLine,
            GameStateProperty gameStateProperty,
            UILabelManager labelManager,
            InputEventListener eventListener,
            GridPane gamePanel,
            Runnable showSpecialShapeDisplay) {
        this.gameTimeLine = gameTimeLine;
        this.gameStateProperty = gameStateProperty;
        this.labelManager = labelManager;
        this.eventListener = eventListener;
        this.gamePanel = gamePanel;
        this.showSpecialShapeDisplay = showSpecialShapeDisplay;
    }

    /**
     * Starts a new game.
     */
    public void startNewGame() {
        gameTimeLine.stop();
        labelManager.hideGameOver();
        labelManager.hidePause();

        eventListener.createNewGame();

        showSpecialShapeDisplay.run();

        gamePanel.requestFocus();
        gameTimeLine.start();
        gameStateProperty.reset();
    }

    /**
     * Toggles pause state.
     */
    public void togglePause() {
        if (!gameStateProperty.isPaused()) {
            pauseGame();
        } else {
            resumeGame();
        }
        gamePanel.requestFocus();
    }

    /**
     * Pauses the game.
     */
    private void pauseGame() {
        gameTimeLine.stop();
        gameStateProperty.setPaused(true);
        labelManager.showPause();
    }

    /**
     * Resumes the game with countdown.
     */
    private void resumeGame() {
        if (!labelManager.isCountdownRunning()) {
            labelManager.startResumeCountdown(() -> {
                gameStateProperty.setPaused(false);
                gameTimeLine.start();
            });
        }
    }

    /**
     * Ends the game (game over).
     */
    public void endGame() {
        gameTimeLine.stop();
        labelManager.showGameOver();
        gameStateProperty.setGameOver(true);
    }
}