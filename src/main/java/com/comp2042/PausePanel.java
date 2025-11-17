package com.comp2042;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

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
