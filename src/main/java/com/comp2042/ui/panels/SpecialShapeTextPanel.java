package com.comp2042.ui.panels;

import com.comp2042.ui.elements.SpecialShapeConfig;
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
 * Displays special shape completion notification with animation.
 * SOLID: Single Responsibility: Only displays special shape notifications
 */
public class SpecialShapeTextPanel extends BorderPane {

    public SpecialShapeTextPanel() {
        setMinHeight(200);
        setMinWidth(300);

        final Label messageLabel = new Label(SpecialShapeConfig.COMPLETION_MESSAGE);
        messageLabel.getStyleClass().add("specialShapeStyle");

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.GOLD);
        dropShadow.setRadius(20);
        messageLabel.setEffect(dropShadow);
        messageLabel.setTextFill(Color.GOLD);

        setCenter(messageLabel);
    }

    /**
     * Animates the special shape completion notification.
     */
    public void showCompletion(ObservableList<Node> list) {
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(400), this);
        scaleUp.setFromX(0.3);
        scaleUp.setFromY(0.3);
        scaleUp.setToX(1.3);
        scaleUp.setToY(1.3);

        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(300), this);
        scaleDown.setFromX(1.3);
        scaleDown.setFromY(1.3);
        scaleDown.setToX(1.0);
        scaleDown.setToY(1.0);

        FadeTransition fadeOut = new FadeTransition(
                Duration.millis(SpecialShapeConfig.MESSAGE_DURATION_MS),
                this
        );
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
                list.remove(SpecialShapeTextPanel.this);
            }
        });

        sequence.play();
    }
}