package com.comp2042.input;

import com.comp2042.ui.GuiController;
import com.comp2042.renderer.Refresh;
import com.comp2042.system_events.EventSource;
import com.comp2042.system_events.EventType;
import com.comp2042.system_events.MoveEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

/**
 * This class was created to separate the user input handling from the initialise() method in GuiController.
 */
public class InputHandler {
    private final GuiController gc;
    private final Refresh rf;
    private final InputEventListener eventListener;
    private final Rectangle[][] rectangles;
    private final GridPane brickPanel;
    private final GridPane gamePanel;

    /**
     * Passes object required for input handling over from GuiController.java.
     * @param eventListener eventListener.
     * @param gc GuiController class to access its methods.
     * @param gamePanel     gamePanel.
     */
    public InputHandler (GuiController gc,
                         InputEventListener eventListener,
                         Rectangle[][] rectangles,
                         GridPane brickPanel,
                         GridPane gamePanel) {
        this.gc = gc;
        this.rf = gc.refresh;
        this.eventListener = eventListener;
        this.rectangles = rectangles;
        this.brickPanel = brickPanel;
        this.gamePanel = gamePanel;
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
     * ----------------------------------REFACTOR IN THE FUTURE----------------------------------<br>
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
        if (gc.getGameStateManager().canProcessInput()) {
            if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
                rf.refreshBrick(eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)), rectangles, brickPanel, gamePanel);
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
                rf.refreshBrick(eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)), rectangles, brickPanel, gamePanel);
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.X) {
                rf.refreshBrick(eventListener.onRotateClock(new MoveEvent(EventType.ROTATE_CLOCK, EventSource.USER)), rectangles, brickPanel, gamePanel);
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.Z) {
                rf.refreshBrick(eventListener.onRotateAntiClock(new MoveEvent(EventType.ROTATE_ANTICLOCK, EventSource.USER)), rectangles, brickPanel, gamePanel);
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
                rf.refreshBrick(eventListener.onRotateAntiClock(new MoveEvent(EventType.ROTATE_ANTICLOCK, EventSource.USER)), rectangles, brickPanel, gamePanel);
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                gc.moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.SPACE) {
                rf.refreshBrick(eventListener.onSnapEvent(new MoveEvent(EventType.SNAP, EventSource.USER)), rectangles, brickPanel, gamePanel);
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.C) {
                gc.onHoldEvent();
                keyEvent.consume();
            }
        }
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            gc.pauseGame(null);
        }
        if (keyEvent.getCode() == KeyCode.N) {
            gc.newGame(null);
        }
    }
}
