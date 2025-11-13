package com.comp2042;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

/**
 * This class was created to separate the user input handling from the initialise() method in GuiController.
 */
public class InputHandler {
    private final GuiController gc;
    private final InputEventListener eventListener;
    private final GridPane gamePanel;
//    private final GridPane brickPanel;
//    private final Rectangle[][] rectangles;

    /**
     * Passes object required for input handling over from GuiController.java.
     * @param eventListener eventListener.
     * @param gc GuiController class to access its methods.
     * @param gamePanel     gamePanel.
     */
    public InputHandler (GuiController gc, InputEventListener eventListener, GridPane gamePanel) {
        this.gc = gc;
        this.eventListener = eventListener;
        this.gamePanel = gamePanel;
//        this.brickPanel = brickPanel;
//        this.rectangles = rectangles;
    }

    /**
     * Note to self: <br>
     * <p>
     *     In Java, lambda function is: (parameters) -> { body } <br>
     *     In JavaScript, lambda function is: "function_name" = (parameters) => function <br>
     *     In Haskell, lambda function is: \x y -> x + y <br>
     * </p>
     * this::handleKeyEvent is a method reference. <br>
     */
    public void setKeyListener() {
        gamePanel.setOnKeyPressed(this::handleKeyEvent);
    }

    /**
     * First checks if the game is paused or is over. <br>
     * If either the game is in either state,
     * <p>
     *     the game will not process any additional keystrokes received from the player
     *     except the new game keystroke, until the game is unpaused if in a paused state.
     * </p>
     * <br>
     * If the game is in neither state, process the directional keystrokes of the player accordingly.<br>
     * <p>
     *     For example, when the player chooses to rotate the Brick-shape-object, either the UP key or the W key
     *     must be entered.<br>
     *     Then, the refreshBrick() method will be called and 2 objects will be passed, an EventType object
     *     representing what the keystroke should do, and an EventSource object to denote that the action
     *     is from the user and not a thread (system's automatic actions).
     * </p>
     * @param keyEvent  Player's directional keystroke.
     */
    private void handleKeyEvent(KeyEvent keyEvent) {
        if (gc.isPause.getValue() == Boolean.FALSE && gc.isGameOver.getValue() == Boolean.FALSE) {
            if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
//                Refresh.refreshBrick(eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)), rectangles, brickPanel, gamePanel);
                gc.refreshBrick(eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)));
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
//                Refresh.refreshBrick(eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)), rectangles, brickPanel, gamePanel);
                gc.refreshBrick(eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)));
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
//                Refresh.refreshBrick(eventListener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER)), rectangles, brickPanel, gamePanel);
                gc.refreshBrick(eventListener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER)));
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                gc.moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
                keyEvent.consume();
            }
        }
        if (keyEvent.getCode() == KeyCode.N) {
            gc.newGame(null);
        }
    }
}
