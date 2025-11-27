package com.comp2042.ui.ui_systems;

import com.comp2042.ui.panels.GameOverPanel;
import com.comp2042.ui.panels.PausePanel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

/**
 * Manages UI panel visibility and states.
 * SOLID: Single Responsibility - Only manages UI panels
 * Design Pattern: Facade - Simplifies panel management
 */
public class UIPanelManager {
    private final GameOverPanel gameOverPanel;
    private final PausePanel pausePanel;
    private boolean countdownRunning = false;

    public UIPanelManager(GameOverPanel gameOverPanel, PausePanel pausePanel) {
        this.gameOverPanel = gameOverPanel;
        this.pausePanel = pausePanel;
    }

    public void showGameOver() {
        gameOverPanel.setVisible(true);
    }

    public void hideGameOver() {
        gameOverPanel.setVisible(false);
    }

    public void showPause() {
        pausePanel.setString("GAME\nPAUSED");
        pausePanel.setVisible(true);
        pausePanel.toFront();
    }

    public void hidePause() {
        pausePanel.setVisible(false);
    }

    public boolean isCountdownRunning() {
        return countdownRunning;
    }

    /**
     * Starts resume countdown with callback.<br>
     * setCycleCount() must be 4 to show the full countdown from 3.<br>
     * Design Pattern: Observer - Callbacks notify when countdown completes
     */
    public void startResumeCountdown(Runnable onComplete) {
        if (countdownRunning) return;
        countdownRunning = true;

        pausePanel.setVisible(true);
        pausePanel.toFront();

        final int[] count = {3};

        Timeline countdown = new Timeline(
                new KeyFrame(javafx.util.Duration.seconds(1), e -> {
                    if (count[0] > 0) {
                        pausePanel.setString("GAME RESUMING IN\n\t" + count[0]);
                        count[0]--;
                    } else {
                        pausePanel.setVisible(false);
                        countdownRunning = false;
                        onComplete.run();
                    }
                })
        );
        countdown.setCycleCount(4);
        countdown.play();
    }
}