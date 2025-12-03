package com.comp2042.ui.panels;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * Displays level-up notifications with animation.
 * SOLID: Single Responsibility: Only displays level-up notifications
 */
public class LevelUpPanel extends BorderPane {

    public LevelUpPanel(int newLevel) {
        setMinHeight(150);
        setMinWidth(250);

        final Label levelLabel = new Label("LEVEL " + newLevel);
        levelLabel.getStyleClass().add("levelUpStyle");

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.GOLD);
        dropShadow.setRadius(15);
        levelLabel.setEffect(dropShadow);
        levelLabel.setTextFill(Color.GOLD);

        setCenter(levelLabel);
    }

    /**
     * Animates the level-up notification
     * Design Pattern: Template Method - Animation sequence is predefined
     */
    public void showLevelUp(ObservableList<Node> list) {
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(300), this);
        scaleUp.setFromX(0.5);
        scaleUp.setFromY(0.5);
        scaleUp.setToX(1.2);
        scaleUp.setToY(1.2);

        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), this);
        scaleDown.setFromX(1.2);
        scaleDown.setFromY(1.2);
        scaleDown.setToX(1.0);
        scaleDown.setToY(1.0);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(1500), this);
        fadeOut.setDelay(Duration.millis(1000));
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        SequentialTransition sequence = new SequentialTransition(
                scaleUp,
                scaleDown,
                fadeOut
        );

        sequence.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                list.remove(LevelUpPanel.this);
            }
        });

        sequence.play();
    }
}