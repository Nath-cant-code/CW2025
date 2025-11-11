package com.comp2042;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * ------------------------------CAN PROBABLY SPLIT THIS CLASS INTO 2 OR MORE CLASSES----------------------------<br>
 * Acts as a bridge between the GUI and the game logic.<br>
 * This class:
 * <p>
 *     - Displays the matrix of the playable area (currentGameMatrix) in a graphical form.<br>
 *     - Listens to player keystrokes.<br>
 *     - Handles the automatic natural falling mechanism of Brick-shape-objects in the game.<br>
 *     - Updates visual layout of playable area when the matrix is altered.<br>
 *     - Handles pause, game over and new game functionalities.
 * </p>
 */
public class GuiController implements Initializable {

    private static final int BRICK_SIZE = 20;

    @FXML
    private GridPane gamePanel;

    @FXML
    private Group groupNotification;

    @FXML
    private GridPane brickPanel;

    @FXML
    private GameOverPanel gameOverPanel;

    private Rectangle[][] displayMatrix;

    private InputEventListener eventListener;

    private Rectangle[][] rectangles;

    private Timeline timeLine;

    private final BooleanProperty isPause = new SimpleBooleanProperty();

    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    /**
     * Initialises the GUI when the FXML file is loaded at the start of the game.<br>
     * Loads a custom font from resources directory.<br>
     * <p>
     *     gamePanel.setFocusTraversable(true);
 *         gamePanel.requestFocus();
     * </p>
     * These lines sets up gamePanel to be ready to receive keyboard inputs.<br>
     * --------------------------------MAYBE CAN SPLIT------------------------------<br>
     * Hides the "GAME OVER" panel during gameplay.<br>
     * -------------------------------[UNUSED]------------------------------<br>
     * Creates Reflection object for effects. <br>
     * ------------------------------CHECK------------------------------<br>
     * parameters unused but do not seem to be able to be removed.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>() {
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
            @Override
            public void handle(KeyEvent keyEvent) {
                if (isPause.getValue() == Boolean.FALSE && isGameOver.getValue() == Boolean.FALSE) {
                    if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
                        refreshBrick(eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
                        refreshBrick(eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
                        refreshBrick(eventListener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                        moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
                        keyEvent.consume();
                    }
                }
                if (keyEvent.getCode() == KeyCode.N) {
                    newGame(null);
                }
            }
        });
        gameOverPanel.setVisible(false);

        final Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);
    }

    /**
     * Called once by GameController at the start of the game.<br>
     * 1. Creates visual playable area (displayMatrix) from boardMatrix (AKA currentGameMatrix).<br>
     * ------------------------------MAYBE CAN SPLIT THESE FUNCTIONALITIES------------------------------<br>
     * 2. Create a box area (rectangles) in which the current Brick-shape-object resides in until it merges with the playable area.<br>
     * 3. Positions bricks at spawn point.<br>
     * 4. Creates a timeline object that automatically causes Brick objects to naturally fall at specific intervals, Duration,millis( x ).
     * @param boardMatrix   Matrix of the playable area (currentGameMatrix in SimpleBoard).
     * @param brick         Object containing info on the current and next in line Brick-shape-object.
     */
    public void initGameView(int[][] boardMatrix, ViewData brick) {
//        1.
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i - 2);
            }
        }

//        2.
        rectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(getFillColor(brick.getBrickData()[i][j]));
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }

//        3.
        brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);

//        4.
        timeLine = new Timeline(new KeyFrame(
                Duration.millis(400),
                ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }

    /**
     * Sets a colour for the Brick-shape-object.
     * @param i Index to choose colour.
     * @return  Color object.
     */
    private Paint getFillColor(int i) {
        Paint returnPaint;
        switch (i) {
            case 0:
                returnPaint = Color.TRANSPARENT;
                break;
            case 1:
                returnPaint = Color.AQUA;
                break;
            case 2:
                returnPaint = Color.BLUEVIOLET;
                break;
            case 3:
                returnPaint = Color.DARKGREEN;
                break;
            case 4:
                returnPaint = Color.YELLOW;
                break;
            case 5:
                returnPaint = Color.RED;
                break;
            case 6:
                returnPaint = Color.BEIGE;
                break;
            case 7:
                returnPaint = Color.BURLYWOOD;
                break;
            default:
                returnPaint = Color.WHITE;
                break;
        }
        return returnPaint;
    }

    /**
     * Only has functionality if game is not paused.<br>
     * Updates the position of the Brick object everytime is naturally falls.<br>
     * Hence, this method has to recolor the orientation of the Brick-shape-object inside the Rectangle box object it resides in.<br>
     * Is also called when the player does any action on the Brick object.<br>
     * @param brick Info on current and next in line Brick-shape-object.
     */
    private void refreshBrick(ViewData brick) {
        if (isPause.getValue() == Boolean.FALSE) {
            brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
            brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);
            for (int i = 0; i < brick.getBrickData().length; i++) {
                for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                    setRectangleData(brick.getBrickData()[i][j], rectangles[i][j]);
                }
            }
        }
    }

    /**
     * Recolours the displayMatrix to match currentGameMatrix.
     * @param board Matrix of playable area (currentGameMatrix in SimpleBoard).
     */
    public void refreshGameBackground(int[][] board) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }

    /**
     * ---------------------------------------CAN BE INLINE?-------------------------------------
     * setRectangleData(src, dest).<br>
     * Updates the colour of each pixel of object passed as dest to match the colour of src.
     * @param color     A single pixel of board (in this class) (AKA playable area, currentGameMatrix) or a Brick-shape-object.
     * @param rectangle A single pixel displayMatrix.
     */
    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }

    /**
     * Only has functionality if game is not paused.<br>
     * Checks if the action of moving the Brick-shape-object down, regardless if caused by system or player,
     * results in at least one row being completely filled.<br>
     * If there is at least one row, pass arguments to NotificationPanel object to display the points obtained by that action alone.<br>
     * Then call refreshBrick() to update visuals as a result of downward action.<br>
     * gamePanel.requestFocus() to ensure the keystrokes are still being taken in by system.
     * @param event MoveEvent object to check if source of action is from system (THREAD) or player (USER).
     */
    private void moveDown(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE) {
            DownData downData = eventListener.onDownEvent(event);
            if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
                NotificationPanel notificationPanel = new NotificationPanel("+" + downData.getClearRow().getScoreBonus());
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
            }
            refreshBrick(downData.getViewData());
        }
        gamePanel.requestFocus();
    }

    /**
     * Connects GUI to game logic.
     * @param eventListener viewGuiController object in GameController.
     */
    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }

    /**
     * -------------------------------------FUNCTIONALITY NEEDED--------------------------------------
     * @param integerProperty   Current SimpleBoard object's overall score, i.e. current game's overall score.
     */
    public void bindScore(IntegerProperty integerProperty) {
    }

    /**
     * Stops the timeLine, unhides game over label, and set game over checker to true.
     */
    public void gameOver() {
        timeLine.stop();
        gameOverPanel.setVisible(true);
        isGameOver.setValue(Boolean.TRUE);
    }

    /**
     * Stops the timeLine, <br>
     * re-hides the game over label, <br>
     * calls createNewGame() method to run code that resets the game, <br>
     * maintain system's capability to receive keystrokes, <br>
     * start the timeLine again, <br>
     * set pause and game over checker boolean values to false. <br>
     * @param actionEvent   null
     */
    public void newGame(ActionEvent actionEvent) {
        timeLine.stop();
        gameOverPanel.setVisible(false);
        eventListener.createNewGame();
        gamePanel.requestFocus();
        timeLine.play();
        isPause.setValue(Boolean.FALSE);
        isGameOver.setValue(Boolean.FALSE);
    }

    /**
     * -------------------------------------FUNCTIONALITY NEEDED--------------------------------------
     * @param actionEvent no uses
     */
    public void pauseGame(ActionEvent actionEvent) {
        gamePanel.requestFocus();
    }
}
