package com.comp2042.ui.systems.controller;

import com.comp2042.logic.engine.Board;
import com.comp2042.logic.game_records.ViewData;
import com.comp2042.bricks.production.blueprints.AbstractBrick;
import com.comp2042.input.event_listener.InputEventListener;
import com.comp2042.input.keyboard.InputHandler;
import com.comp2042.renderer.runtime_refreshers.RefreshCoordinator;
import com.comp2042.ui.systems.initializers.GameInitializer;
import com.comp2042.ui.systems.initializers.PanelInitialiser;
import com.comp2042.ui.systems.managers.*;
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
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import com.comp2042.ui.panels.SpecialShapeDisplayPanel;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * ------------------------------REFACTORED------------------------------<br>
 * SOLID: Single Responsibility: Coordinates UI components<br>
 * SOLID: Interface Segregation: Contains methods not in GameView interface<br>
 * SOLID: Dependency Inversion: Objects of GameView are created and depended on instead of depending on details in this class<br>
 * Design Pattern: Facade: Simple interface to complex UI subsystem<br>
 * ----------------------------------------CLASS SPLIT ALERT--------------------------------------<br>
 * The handle() method here was removed and put into a new class, InputHandler.java to increase cohesion of initialize() method.<br>
 * NOTE: InputHandler object is created in setEventListener() method. <br>
 * ----------------------------------------CLASS SPLIT ALERT--------------------------------------<br>
 * ----------------------------------------FUNCTIONALITY SPLIT ALERT--------------------------------------<br>
 * GuiController no longer handles checking of game states: pause or game over<br>
 * This is now handled in GameStateManager.<br>
 * ----------------------------------------FUNCTIONALITY SPLIT ALERT--------------------------------------<br>
 * Acts as a bridge between the GUI and the game logic.<br>
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
    @FXML   private VBox specialShapeContainer;

    protected Rectangle[][] rectangles;
    public Rectangle[][] displayMatrix;
    public Rectangle[][] holdMatrix;
    public Rectangle[][] previewMatrix;

    public Board board;
    private InputEventListener eventListener;
    protected InputHandler inputHandler;
    private UILabelManager labelManager;
    public RefreshCoordinator refreshCoordinator;
    private SpecialShapeDisplayPanel specialShapeDisplayPanel;

    private final GameStateProperty gameStateProperty = new GameStateProperty();
    protected TimeLineManager timeLineManager = new TimeLineManager();
    private final PanelInitialiser panelInitialiser = new PanelInitialiser();
    private final GameInitializer gameInitializer = new GameInitializer();
    private SpecialShapeManager specialShapeManager;
    private GameStateManager gameStateManager;

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

        refreshCoordinator = new RefreshCoordinator(BRICK_SIZE, gameStateProperty);
        labelManager = new UILabelManager(gameOverPanel, pausePanel);

        labelManager.hideAll();
        holdMatrix = panelInitialiser.initializeHoldPanel(holdPanel);
        previewMatrix = panelInitialiser.initializePreviewPanel(previewPanel);

        specialShapeDisplayPanel = new SpecialShapeDisplayPanel();
        if (specialShapeContainer != null) {
            specialShapeContainer.getChildren().add(specialShapeDisplayPanel);
        }

        final Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);
    }

    /**
     * Called once by EventListener at the start of the game.<br>
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
     * ------------------------------SPLIT TO NEW CLASS: TimeLineManager.java ------------------------------<br>
     * 5. Creates a timeline object that automatically causes Brick objects to naturally fall at specific intervals, Duration,millis( x ).<br>
     * All of this now happens in TimeLineManager.java.
     * @param boardMatrix   Matrix of the playable area (currentGameMatrix in SimpleBoard).
     * @param brick         Object containing info on the current and next in line Brick-shape-object.
     */
    @Override
    public void initGameView(int[][] boardMatrix, ViewData brick) {
        displayMatrix = gameInitializer.initialiseBoard(gamePanel, boardMatrix);
        rectangles = gameInitializer.initialiseBrickArea(brickPanel, brick);
        gameInitializer.positionBrickPanel(brickPanel, gamePanel, brick);
        inputHandler = gameInitializer.createInputHandler(
                this,
                eventListener,
                rectangles,
                brickPanel,
                gamePanel);

        timeLineManager.setGameTimeline(eventListener);
        timeLineManager.start();

        initializeManagers();
    }

    /**
     * Initialises manager classes.
     */
    private void initializeManagers() {
        specialShapeManager = new SpecialShapeManager(
                board,
                timeLineManager,
                gameStateProperty,
                gamePanel
        );

        gameStateManager = new GameStateManager(
                timeLineManager,
                gameStateProperty,
                labelManager,
                eventListener,
                gamePanel,
                this::restoreSpecialShapeDisplay
        );
    }

    /**
     * This method is passed to GameStateManager to be executed in its process flow.
     */
    private void restoreSpecialShapeDisplay() {
        if (!isSpecialShapeDisplayVisible() && specialShapeDisplayPanel != null) {
            specialShapeContainer.getChildren().add(specialShapeDisplayPanel);
        }
    }

    /**
     * Connects GUI to game logic.
     * @param eventListener viewGuiController object in EventListener.
     */
    @Override
    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }

    /**
     * Creates SimpleBoard object in this class.<br>
     * Refreshes Hold Brick panel and Preview Bricks panel.
     * @param board SimpleBoard object.
     */
    public void setBoard(Board board) {
        this.board = board;
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

    /**
     * Calls method to update (increase) fall speed
     * @param speedMs   Current falling speed of Brick object
     */
    @Override
    public void updateFallSpeed(int speedMs) {
        timeLineManager.updateSpeed(speedMs, eventListener);
    }

    /**
     * Binds level text to level label at the side
     * @param levelProperty Current level
     */
    @Override
    public void bindLevel(IntegerProperty levelProperty) {
        levelLabel.textProperty().bind(levelProperty.asString("Level: %d"));
    }

    /**
     * Calls refresh Brick method
     * @param viewData  Brick object info
     */
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

    /**
     * Calls render method for rendering Brick object in hold panel.
     */
    @Override
    public void refreshHoldBrick () {
        refreshCoordinator.renderHoldBrick((AbstractBrick) board.getHeldBrick(), holdMatrix, holdPanel);
    }

    /**
     * Calls render method for rendering Brick objects in queue in preview panel.
     */
    @Override
    public void refreshPreviewPanel () {
        refreshCoordinator.renderNextBricks(board.getNextBricksPreview(), previewMatrix);
    }

    /**
     * Calls method containing the logic that handles UI for when special combination shape is formed
     */
    @Override
    public void handleSpecialShapeCompletion() {
        specialShapeManager.handleCompletion(rectangles, this, groupNotification.getChildren());
    }

    /**
     * Hides display panel of the special combination shape
     */
    @Override
    public void hideSpecialShapeDisplay() {
        if (specialShapeContainer != null && specialShapeDisplayPanel != null) {
            specialShapeContainer.getChildren().remove(specialShapeDisplayPanel);
        }
    }

    /**
     * Checks if the display panel of the special combination shape is visible
     * @return TRUE if there are elements in specialShapeContainer
     */
    @Override
    public boolean isSpecialShapeDisplayVisible() {
        return specialShapeContainer != null &&
                specialShapeContainer.getChildren().contains(specialShapeDisplayPanel);
    }

    /**
     * Retrieve GameStateManager object.
     * @return  GameStateManager object.
     */
    public GameStateProperty getGameStateManager() {
        return gameStateProperty;
    }

    /**
     * Calls method that contains logic sequence of starting a new game.
     */
    public void newGame(ActionEvent actionEvent) {
        gameStateManager.startNewGame();
    }

    /**
     * -------------------------------------FUNCTIONALITY ADDED--------------------------------------<br>
     * Calls said functionality in gameStateManager.
     * @param actionEvent no uses
     */
    public void pauseGame (ActionEvent actionEvent) {
        gameStateManager.togglePause();
    }

    /**
     * Calls delegated method containing logic that processes when a game is over.
     */
    public void gameOver() {
        gameStateManager.endGame();
    }
}
