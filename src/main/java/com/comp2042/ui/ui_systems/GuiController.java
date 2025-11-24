package com.comp2042.ui.ui_systems;

import com.comp2042.board.composite_bricks.DownData;
import com.comp2042.board.SimpleBoard;
import com.comp2042.board.composite_bricks.ViewData;
import com.comp2042.bricks.AbstractBrick;
import com.comp2042.input.InputEventListener;
import com.comp2042.input.InputHandler;
import com.comp2042.renderer.BoardRenderer;
import com.comp2042.renderer.BrickRenderer;
import com.comp2042.renderer.RefreshCoordinator;
import com.comp2042.system_events.MoveEvent;
import com.comp2042.ui.GameTimeLine;
import com.comp2042.ui.GameView;
import com.comp2042.ui.panels.GameOverPanel;
import com.comp2042.ui.panels.NotificationPanel;
import com.comp2042.ui.panels.PausePanel;
import javafx.animation.KeyFrame;
import javafx.beans.property.IntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import javafx.animation.Timeline;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * ----------------------------------------CLASS SPLIT ALERT--------------------------------------<br>
 * The handle() method here was removed and put into a new class, InputHandler.java to increase cohesion of initialise() method.<br>
 * NOTE: InputHandler object is created in setEventListener() method. <br>
 * ----------------------------------------CLASS SPLIT ALERT--------------------------------------<br>
 * ----------------------------------------FUNCTIONALITY SPLIT ALERT--------------------------------------<br>
 * GuiController no longer handles checking of game states: pause or game over<br>
 * This is now handled in GameStateManager.<br>
 * ----------------------------------------FUNCTIONALITY SPLIT ALERT--------------------------------------<br>
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
public class GuiController implements Initializable, GameView {

    public static final int BRICK_SIZE = 20;

    @FXML
    public GridPane gamePanel;

    @FXML
    private Group groupNotification;

    @FXML
    private GridPane brickPanel;

    @FXML
    private GameOverPanel gameOverPanel;

    public Rectangle[][] displayMatrix;

    private InputEventListener eventListener;

    protected Rectangle[][] rectangles;

    private final GameStateManager gameStateManager = new GameStateManager();

    /**
     * New class objects created.
     */
    public SimpleBoard simpleBoard;

    protected InputHandler inputHandler;

    protected BoardRenderer boardRenderer = new BoardRenderer();

    protected BrickRenderer brickRenderer = new BrickRenderer();

    protected GameTimeLine gameTimeLine = new GameTimeLine();

    public RefreshCoordinator refreshCoordinator;

    @FXML
    protected PausePanel pausePanel;

    @FXML
    private Label scoreLabel;

    private boolean countdownRunning = false;

    @FXML
    public GridPane holdPanel;

    public Rectangle[][] holdMatrix;

    @FXML
    public GridPane nextPanel;

    public Rectangle[][] nextMatrix;

    /**
     * Initialises the GUI when the FXML file is loaded at the start of the game.<br>
     * Loads a custom font from resources directory.<br>
     * <p>
     *     gamePanel.setFocusTraversable(true);
 *         gamePanel.requestFocus();
     * </p>
     * Hides the "GAME OVER" panel during gameplay.<br>
     * Creates Reflection object for effects. <br>
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(Objects.requireNonNull(getClass().getClassLoader().getResource("digital.ttf")).toExternalForm(), 38);
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();

        refreshCoordinator = new RefreshCoordinator(BRICK_SIZE, gameStateManager);

        gameOverPanel.setVisible(false);
        pausePanel.setVisible(false);

        initHoldMatrix();

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

        initNextPanel();

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
     * Clearer more concise initialise method.
     * @param boardMatrix The initial board matrix
     * @param initialBrick The first brick to display
     */
    @Override
    public void initialise(int[][] boardMatrix, ViewData initialBrick) {
        initGameView(boardMatrix, initialBrick);
    }

    /**
     * Connects GUI to game logic.
     * @param eventListener viewGuiController object in GameController.
     */
    @Override
    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }

    /**
     * -------------------------------------FUNCTIONALITY ADDED--------------------------------------
     * @param integerProperty   Current SimpleBoard object's overall score, i.e. current game's overall score.
     */
    @Override
    public void bindScore(IntegerProperty integerProperty) {
        scoreLabel.textProperty().bind(integerProperty.asString());
    }

    /**
     * Show points obtained everytime a row is cleared.
     * @param points The points earned
     */
    @Override
    public void showClearRowNotification(int points) {
        NotificationPanel notificationPanel = new NotificationPanel("+" + points);
        groupNotification.getChildren().add(notificationPanel);
        notificationPanel.showScore(groupNotification.getChildren());
    }

    /**
     * Concise method that just calls gameOver.
     */
    @Override
    public void notifyGameOver() {
        gameOver();
    }

    /**
     * Retrieves displayMatrix.
     * @return  displayMatrix.
     */
    @Override
    public Rectangle[][] getDisplayMatrix() {
        return displayMatrix;
    }

    /**
     * Concise method to update (refresh) background.
     * @param boardMatrix The current board state.
     */
    @Override
    public void updateBackground(int[][] boardMatrix) {
        refreshCoordinator.renderBackground(boardMatrix, displayMatrix);
    }

    private void initNextPanel() {
        BrickRenderer renderer = new BrickRenderer();

        // Create an empty 12x4 matrix (your preview size)
        int rows = 12;
        int cols = 4;

        int[][] emptyMatrix = new int[rows][cols]; // all zeros

        // ViewData requires 4 parameters, so we supply all of them
        ViewData emptyView = new ViewData(
                emptyMatrix, // brickData
                0,           // xPosition (unused in preview)
                0,           // yPosition (unused in preview)
                emptyMatrix  // nextBrickData (not important for preview)
        );

        // Only one nextPanel, only one nextMatrix
        nextMatrix = renderer.createBrickAreaMatrix(nextPanel, emptyView);
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
    public void moveDown(MoveEvent event) {
        if (!gameStateManager.isPaused()) {
            DownData downData = eventListener.onDownEvent(event);
            if (downData.clearRow() != null && downData.clearRow().linesRemoved() > 0) {
                NotificationPanel notificationPanel = new NotificationPanel("+" + downData.clearRow().scoreBonus());
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
            }
            refreshCoordinator.renderActiveBrick(downData.viewData(), rectangles, brickPanel, gamePanel);
            refreshCoordinator.renderNextBricks(simpleBoard.getNextBricksPreview(), nextMatrix);
        }
        gamePanel.requestFocus();
    }

    /**
     * Creates SimpleBoard object in this class.<br>
     * Refreshes Hold Brick panel and Preview Bricks panel.
     * @param simpleBoard SimpleBoard object.
     */
    public void setSimpleBoard (SimpleBoard simpleBoard) {
        this.simpleBoard = simpleBoard;
        refreshCoordinator.renderHoldBrick((AbstractBrick) simpleBoard.getHeldBrick(), holdMatrix, holdPanel);
        refreshCoordinator.renderNextBricks(simpleBoard.getNextBricksPreview(), nextMatrix);
    }

    /**
     * Retrieve GameStateManager object.
     * @return  GameStateManager object.
     */
    public GameStateManager getGameStateManager() {
        return gameStateManager;
    }

    /**
     * Stops the timeLine, unhides game over label, and set game over checker to true.
     */
    public void gameOver() {
        gameTimeLine.stop();
        gameOverPanel.setVisible(true);
        gameStateManager.setGameOver(true);
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
        pausePanel.setVisible(false);
        eventListener.createNewGame();
        gamePanel.requestFocus();
        gameTimeLine.start();
        gameStateManager.reset();
    }

    /**
     * -------------------------------------FUNCTIONALITY ADDED--------------------------------------<br>
     * If game is running and user presses pause key, boolean paused = true -> runs true if statement
     * @param actionEvent no uses
     */
    public void pauseGame (ActionEvent actionEvent) {

        if (!gameStateManager.isPaused()) {
            gameTimeLine.stop();
            gameStateManager.setPaused(true);
            pausePanel.setString("GAME\nPAUSED");
            pausePanel.setVisible(true);
            pausePanel.toFront();
        }
        else {
            if (!countdownRunning) {
                startResumeCountdown();
            }
        }
        gamePanel.requestFocus();
    }

    /**
     * Start a countdown from 3 when player presses the resume key.
     */
    private void startResumeCountdown () {
        if (countdownRunning) return;
        countdownRunning = true;

        pausePanel.setVisible(true);
        pausePanel.toFront();

        final int[] count = {3};

        Timeline countdown = new Timeline(
                new KeyFrame(javafx.util.Duration.seconds(1), e -> {
                    if (count[0] > 0) {
                        pausePanel.setString("GAME RESUMING IN\n\t" + count[0]);
                        count[0]--;
                    }
                    else {
                        pausePanel.setVisible(false);
                        gameStateManager.setPaused(false);
                        countdownRunning = false;
                        gameTimeLine.start();
                    }
                })
        );
        countdown.setCycleCount(4);
        countdown.play();
    }

    /**
     * Initialises a hold matrix in the hold panel
     */
    private void initHoldMatrix () {
        int size = 4;
        holdMatrix = new Rectangle[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Rectangle r = new Rectangle(BRICK_SIZE,BRICK_SIZE);
                r.setFill(Color.TRANSPARENT);
                holdMatrix[i][j] = r;
                holdPanel.add(r, i, j);
            }
        }
    }

    /**
     * Refreshes the hold panel to contain the selected held Brick.
     */
    public void onHoldEvent () {
        if (simpleBoard == null) return;

        simpleBoard.holdBrick();
        refreshCoordinator.renderHoldBrick((AbstractBrick) simpleBoard.getHeldBrick(), holdMatrix, holdPanel);

        ViewData viewData = simpleBoard.getViewData();
        refreshCoordinator.renderActiveBrick(viewData, rectangles, brickPanel, gamePanel);
    }
}
