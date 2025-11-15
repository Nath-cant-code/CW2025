package com.comp2042;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.Objects;
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

    protected static final int BRICK_SIZE = 20;

    @FXML
    private GridPane gamePanel;

    @FXML
    private Group groupNotification;

    @FXML
    private GridPane brickPanel;

    @FXML
    private GameOverPanel gameOverPanel;

    protected Rectangle[][] displayMatrix;

    private InputEventListener eventListener;

    protected Rectangle[][] rectangles;

    protected final BooleanProperty isPause = new SimpleBooleanProperty();

    protected final BooleanProperty isGameOver = new SimpleBooleanProperty();

    /**
     * New class objects created.
     */
    protected InputHandler inputHandler;

    protected BoardRenderer boardRenderer = new BoardRenderer();

    protected BrickRenderer brickRenderer = new BrickRenderer();

    protected GameTimeLine gameTimeLine = new GameTimeLine();

    protected Refresh refresh = new Refresh(this);

    /**
     * Initialises the GUI when the FXML file is loaded at the start of the game.<br>
     * Loads a custom font from resources directory.<br>
     * <p>
     *     gamePanel.setFocusTraversable(true);
 *         gamePanel.requestFocus();
     * </p>
     * ----------------------------------------CLASS SPLIT ALERT--------------------------------------<br>
     * The handle() method here was removed and put into a new class, InputHandler.java to increase cohesion of initialise() method.<br>
     * NOTE: InputHandler object is created in setEventListener() method. <br>
     * ----------------------------------------CLASS SPLIT ALERT--------------------------------------<br>
     * Hides the "GAME OVER" panel during gameplay.<br>
     * Creates Reflection object for effects. <br>
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
        Font.loadFont(Objects.requireNonNull(getClass().getClassLoader().getResource("digital.ttf")).toExternalForm(), 38);
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();

        gameOverPanel.setVisible(false);

        final Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);
    }

    /**
     * Called once by GameController at the start of the game.<br>
     * -------------------------FUNCTIONALITIES MAINTAINED, BUT LOGIC MOVED TO NEW CLASSES-------------------------<br>
     * ------------------------------SPLIT TO NEW CLASS: BoardRenderer.java ------------------------------<br>
     * 1. Creates visual playable area (displayMatrix) from boardMatrix (AKA currentGameMatrix).<br>
     *
     * ------------------------------SPLIT TO NEW CLASS: BrickRenderer.java ------------------------------<br>
     * 2. Create a box area (rectangles) in which the current Brick-shape-object resides in until it merges with the playable area.<br>
     *
     * 3. Positions bricks at spawn point.<br>
     *
     * ------------------------------NEW CLASS: InputHandler.java ------------------------------<br>
     * 4. Creates an InputHandler object here.<br>
     * ***IMPORTANT*** Object creation used to be in setEventListener() which caused lag due to rectangles being passed as null.<br>
     *
     * ------------------------------SPLIT TO NEW CLASS: GameTimeLine.java ------------------------------<br>
     * 5. Creates a timeline object that automatically causes Brick objects to naturally fall at specific intervals, Duration,millis( x ).<br>
     * All of this now happens in GameTimeLine.java.
     * @param boardMatrix   Matrix of the playable area (currentGameMatrix in SimpleBoard).
     * @param brick         Object containing info on the current and next in line Brick-shape-object.
     */
    public void initGameView(int[][] boardMatrix, ViewData brick) {
//        1.
        displayMatrix = boardRenderer.createPlayableAreaMatrix(gamePanel, boardMatrix);

//        2.
        rectangles = brickRenderer.createBrickAreaMatrix(brickPanel, brick);

//        3.
        brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.xPosition() * brickPanel.getVgap() + brick.xPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.yPosition() * brickPanel.getHgap() + brick.yPosition() * BRICK_SIZE);

//        4.
        inputHandler = new InputHandler(
                this,
                eventListener,
                rectangles,
                brickPanel,
                gamePanel);
        inputHandler.setKeyListener();

//        5.
        gameTimeLine.setGameTimeline(this);
        gameTimeLine.start();
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
    protected void moveDown(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE) {
            DownData downData = eventListener.onDownEvent(event);
            if (downData.clearRow() != null && downData.clearRow().linesRemoved() > 0) {
                NotificationPanel notificationPanel = new NotificationPanel("+" + downData.clearRow().scoreBonus());
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
            }
            refresh.refreshBrick(downData.viewData(), rectangles, brickPanel, gamePanel);
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
        gameTimeLine.stop();
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
        gameTimeLine.stop();
        gameOverPanel.setVisible(false);
        eventListener.createNewGame();
        gamePanel.requestFocus();
        gameTimeLine.start();
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
