package com.comp2042;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * This class is for displaying the points obtained by the player on the screen.
 */
public class NotificationPanel extends BorderPane {

    /**
     * This constructor does the following upon this class being called:<br>
     * Adjusts the size of the notification. <br>
     * Creates a Label object that takes in the param text containing the points obtained
     * and adds a style to it.<br>
     * Create a Effect object and set it as an effect for Label object.<br>
     * Adjust colour and position of label.<br>
     * @param text  A string passed from GuiController that contains "+" and the points obtained.
     */
    public NotificationPanel(String text) {
        setMinHeight(200);
        setMinWidth(220);
        final Label score = new Label(text);
        score.getStyleClass().add("bonusStyle");
        final Effect glow = new Glow(0.6);
        score.setEffect(glow);
        score.setTextFill(Color.WHITE);
        setCenter(score);

    }

    /**
     * This method creates an object that adds an effect to the score Label's display (transition)
     * and another object to determines the direction of the first effect (i.e. which way should
     * Label fade towards). <br>
     * This method then creates another object to combine the first 2 effects. <br>
     *
     * Self note:<br>
     * transition.setOnFinished() creates an EventHandler to listen for when the transition fully completes.<br>
     * When the transition fully completes, the label is still technically there, just invisible.<br>
     * So the handle() method removes the NotificationPanel object created by calling this class from the game screen.
     * @param list  Object (NotificationPanel) being displayed on the screen.
     */
    public void showScore(ObservableList<Node> list) {
        FadeTransition ft = new FadeTransition(Duration.millis(2000), this);
        TranslateTransition tt = new TranslateTransition(Duration.millis(2500), this);
        tt.setToY(this.getLayoutY() - 40);
        ft.setFromValue(1);
        ft.setToValue(0);
        ParallelTransition transition = new ParallelTransition(tt, ft);
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                list.remove(NotificationPanel.this);
            }
        });
        transition.play();
    }
}
