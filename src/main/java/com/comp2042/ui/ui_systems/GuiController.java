package com.comp2042.ui.ui_systems;

import com.comp2042.board.SimpleBoard;
import com.comp2042.board.composite_bricks.ViewData;
import com.comp2042.bricks.AbstractBrick;
import com.comp2042.bricks.Brick;
import com.comp2042.input.event_controllers.InputEventListener;
import com.comp2042.input.InputHandler;
import com.comp2042.renderer.concrete_refreshers.RefreshCoordinator;
import com.comp2042.ui.GameTimeLine;
import com.comp2042.ui.panels.GameOverPanel;
import com.comp2042.ui.panels.LevelUpPanel;
import com.comp2042.ui.panels.NotificationPanel;
import com.comp2042.ui.panels.PausePanel;
import javafx.beans.property.IntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * ------------------------------REFACTORED------------------------------<br>
 * SOLID: Single Responsibility - Coordinates UI components<br>
 * Design Pattern: Facade - Simple interface to complex UI subsystem<br>
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

    @FXML   public GridPane gamePanel;
    @FXML   private Group groupNotification;
    @FXML   private GridPane brickPanel;
    @FXML   private GameOverPanel gameOverPanel;
    @FXML   protected PausePanel pausePanel;
    @FXML   private Label scoreLabel;
    @FXML   public GridPane holdPanel;
    @FXML   public GridPane previewPanel;
    @FXML   private Label levelLabel;

    protected Rectangle[][] rectangles;
    public Rectangle[][] displayMatrix;
    public Rectangle[][] holdMatrix;
    public Rectangle[][] previewMatrix;

    protected InputHandler inputHandler;
    private UILabelManager labelManager;
    public RefreshCoordinator refreshCoordinator;

    public SimpleBoard simpleBoard;
    private InputEventListener eventListener;
    private final GameStateManager gameStateManager = new GameStateManager();
    protected GameTimeLine gameTimeLine = new GameTimeLine();
    private final PanelInitialiser panelInitialiser = new PanelInitialiser();
    private final GameInitialiser gameInitialiser = new GameInitialiser();


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
        labelManager = new UILabelManager(gameOverPanel, pausePanel);

        labelManager.hideAll();
        holdMatrix = panelInitialiser.initializeHoldPanel(holdPanel);
        previewMatrix = panelInitialiser.initializePreviewPanel(previewPanel);

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
        displayMatrix = gameInitialiser.initialiseBoard(gamePanel, boardMatrix);
        rectangles = gameInitialiser.initialiseBrickArea(brickPanel, brick);
        gameInitialiser.positionBrickPanel(brickPanel, gamePanel, brick);
        inputHandler = gameInitialiser.createInputHandler(
                this,
                eventListener,
                rectangles,
                brickPanel,
                gamePanel);

        gameTimeLine.setGameTimeline(eventListener);
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
     * Creates SimpleBoard object in this class.<br>
     * Refreshes Hold Brick panel and Preview Bricks panel.
     * @param simpleBoard SimpleBoard object.
     */
    public void setSimpleBoard (SimpleBoard simpleBoard) {
        this.simpleBoard = simpleBoard;
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

    @Override
    public void notifyLevelUp(int newLevel) {
        LevelUpPanel levelUpPanel = new LevelUpPanel(newLevel);
        groupNotification.getChildren().add(levelUpPanel);
        levelUpPanel.showLevelUp(groupNotification.getChildren());
    }

    @Override
    public void updateFallSpeed(int speedMs) {
        gameTimeLine.updateSpeed(speedMs, eventListener);
    }

    @Override
    public void bindLevel(IntegerProperty levelProperty) {
        levelLabel.textProperty().bind(levelProperty.asString("Level: %d"));
    }

    @Override
    public void refreshActiveBrick (ViewData viewData) {
        refreshCoordinator.renderActiveBrick(viewData, rectangles, brickPanel, gamePanel);
    }

    /**
     * Concise method to update (refresh) background.
     * @param boardMatrix The current board state.
     */
    @Override
    public void refreshBackground(int[][] boardMatrix) {
        refreshCoordinator.renderBackground(boardMatrix, displayMatrix);
    }

    @Override
    public void refreshHoldBrick () {
        refreshCoordinator.renderHoldBrick((AbstractBrick) simpleBoard.getHeldBrick(), holdMatrix, holdPanel);
    }

    @Override
    public void refreshPreviewPanel () {
        refreshCoordinator.renderNextBricks(simpleBoard.getNextBricksPreview(), previewMatrix);
    }

    /**
     * Retrieve GameStateManager object.
     * @return  GameStateManager object.
     */
    public GameStateManager getGameStateManager() {
        return gameStateManager;
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
        labelManager.hideGameOver();
        labelManager.hidePause();
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
            labelManager.showPause();
        }
        else {
            if (!labelManager.isCountdownRunning()) {
//                passes methods that startResumeCountdown() executes at the end of its functionality
                labelManager.startResumeCountdown(() -> {
                    gameStateManager.setPaused(false);
                    gameTimeLine.start();
                });
            }
        }
        gamePanel.requestFocus();
    }

    /**
     * Stops the timeLine, unhides game over label, and set game over checker to true.
     */
    public void gameOver() {
        gameTimeLine.stop();
        labelManager.showGameOver();
        gameStateManager.setGameOver(true);
    }
}
