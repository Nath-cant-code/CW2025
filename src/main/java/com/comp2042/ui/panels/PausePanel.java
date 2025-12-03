package com.comp2042.ui.panels;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * Handles pause panel.
 * SOLID: Single Responsibility: Only handles pause panel and not other panels.
 */
public class PausePanel extends BorderPane {
    private final Label pauseLabel;

    public PausePanel () {
        pauseLabel = new Label("GAME\nPAUSED");
        pauseLabel.getStyleClass().add("pausePanel");
        setCenter(pauseLabel);
    }

    public void setString (String s) {
        pauseLabel.setText(s);
    }
}
