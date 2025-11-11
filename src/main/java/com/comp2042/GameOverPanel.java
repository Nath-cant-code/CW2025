package com.comp2042;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * Creates and designs Label object (node) for game over screen.
 */
public class GameOverPanel extends BorderPane {

    /**
     * This constructor creates a designed Label object everytime the class is called.
     */
    public GameOverPanel() {
        final Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.getStyleClass().add("gameOverStyle");
        setCenter(gameOverLabel);
    }

}
