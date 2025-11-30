package com.comp2042.ui.systems.managers;

import com.comp2042.input.event_listener.InputEventListener;
import javafx.scene.layout.GridPane;

/**
 * Manages game state operations (new game, pause, resume, game over).<br>
 * SOLID: Single Responsibility - Only manages game state<br>
 * SOLID: Open/Closed - Easy to add new game state operations<br>
 * Design Pattern: Command - Each operation is a distinct command<br>
 */
public class GameStateManager {
    private final TimeLineManager timeLineManager;
    private final GameStateProperty gameStateProperty;
    private final UILabelManager labelManager;
    private final InputEventListener eventListener;
    private final GridPane gamePanel;
    private final Runnable showSpecialShapeDisplay;

    public GameStateManager(
            TimeLineManager timeLineManager,
            GameStateProperty gameStateProperty,
            UILabelManager labelManager,
            InputEventListener eventListener,
            GridPane gamePanel,
            Runnable showSpecialShapeDisplay) {
        this.timeLineManager = timeLineManager;
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
        timeLineManager.stop();
        labelManager.hideGameOver();
        labelManager.hidePause();

        eventListener.createNewGame();

        showSpecialShapeDisplay.run();

        gamePanel.requestFocus();
        timeLineManager.start();
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
        timeLineManager.stop();
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
                timeLineManager.start();
            });
        }
    }

    /**
     * Ends the game (game over).
     */
    public void endGame() {
        timeLineManager.stop();
        labelManager.showGameOver();
        gameStateProperty.setGameOver(true);
    }
}