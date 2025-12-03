package com.comp2042.ui.systems.master;

import com.comp2042.logic.engine.Board;
import com.comp2042.logic.game_records.ViewData;
import com.comp2042.input.event_listener.InputEventListener;
import com.comp2042.input.keyboard.InputHandler;
import com.comp2042.renderer.runtime_refreshers.RefreshCoordinator;
import com.comp2042.ui.systems.initializers.GameInitializer;
import com.comp2042.ui.systems.initializers.PanelInitialiser;
import com.comp2042.ui.systems.managers.*;
import com.comp2042.ui.panels.GameOverPanel;
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
 * ----------------------------------------NEW CLASS----------------------------------------<br>
 * SOLID: Single Responsibility: Manages UI objects and states<br>
 * SOLID: Open Closed: New functionalities can be added in GUIController without affecting fixed handling of UI in this class<br>
 * SOLID: Liskov Substitution: Subclass GUIController extends this class.<br>
 * SOLID: Dependency Inversion: Objects of GameView are created and depended on instead of depending on details in this class<br>
 * Design Pattern: Facade: Simple interface to complex UI subsystem<br>
 * ----------------------------------------CLASS SPLIT ALERT--------------------------------------<br>
 * The handle() method here was removed and put into a new class, InputHandler.java to increase cohesion of initialize() method.<br>
 * NOTE: InputHandler object is created in setEventListener() method. <br>
 * ----------------------------------------FUNCTIONALITY SPLIT ALERT--------------------------------------<br>
 * GuiManager no longer handles logic for game states: pause or game over<br>
 * This is now handled in GameStateManager.<br>
 */
public abstract class GUIManager implements Initializable, GameView {

    public static final int BRICK_SIZE = 20;

    @FXML   public GridPane gamePanel;
    @FXML   protected Group groupNotification;
    @FXML   protected GridPane brickPanel;
    @FXML   private GameOverPanel gameOverPanel;
    @FXML   protected PausePanel pausePanel;
    @FXML   private Label scoreLabel;
    @FXML   public GridPane holdPanel;
    @FXML   public GridPane previewPanel;
    @FXML   protected Label levelLabel;
    @FXML   protected VBox specialShapeContainer;

    protected Rectangle[][] rectangles;
    public Rectangle[][] displayMatrix;
    public Rectangle[][] holdMatrix;
    public Rectangle[][] previewMatrix;

    public Board board;
    protected InputEventListener eventListener;
    protected InputHandler inputHandler;
    private UILabelManager labelManager;
    protected RefreshCoordinator refreshCoordinator;
    protected SpecialShapeDisplayPanel specialShapeDisplayPanel;

    private final GameStateProperty gameStateProperty = new GameStateProperty();
    protected TimeLineManager timeLineManager = new TimeLineManager();
    private final PanelInitialiser panelInitialiser = new PanelInitialiser();
    private final GameInitializer gameInitializer = new GameInitializer();
    protected SpecialShapeManager specialShapeManager;
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
     * Creates SimpleBoard object in this class.<br>
     * Refreshes Hold Brick panel and Preview Bricks panel.
     * @param board SimpleBoard object.
     */
    @Override
    public void setBoard(Board board) {
        this.board = board;
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
     * Retrieve GameStateManager object.
     * @return  GameStateManager object.
     */
    @Override
    public GameStateProperty getGameStateManager() {
        return gameStateProperty;
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
     * Retrieves refreshCoordinator object.
     * @return  refreshCoordinator
     */
    @Override
    public RefreshCoordinator getRefreshCoordinator () {
        return refreshCoordinator;
    }

    /**
     * Retrieves game panel object.
     * @return  game panel
     */
    @Override
    public GridPane getGamePanel () {
        return gamePanel;
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
     * Calls method that contains logic sequence of starting a new game.
     */
    @Override
    public void newGame(ActionEvent actionEvent) {
        gameStateManager.startNewGame();
    }

    /**
     * -------------------------------------FUNCTIONALITY ADDED--------------------------------------<br>
     * Calls said functionality in gameStateManager.
     * @param actionEvent no uses
     */
    @Override
    public void pauseGame (ActionEvent actionEvent) {
        gameStateManager.togglePause();
    }

    /**
     * Calls delegated method containing logic that processes when a game is over.
     */
    @Override
    public void gameOver() {
        gameStateManager.endGame();
    }
}
