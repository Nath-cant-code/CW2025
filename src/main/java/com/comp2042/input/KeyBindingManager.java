package com.comp2042.input;

import com.comp2042.input.event_controllers.InputEventListener;
import com.comp2042.renderer.concrete_refreshers.RefreshCoordinator;
import com.comp2042.system_events.EventSource;
import com.comp2042.system_events.EventType;
import com.comp2042.system_events.MoveEvent;
import com.comp2042.ui.ui_systems.GuiController;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages key bindings (mappings via HashMap) and creates input actions.<br>
 * This class extracts the object initialisations out of InputHandler.java.<br>
 * SOLID: Single Responsibility<br>
 * <p>
 *     - Only responsible for key-to-action mapping<br>
 *     - Makes it easy to change controls or add new bindings<br>
 * </p>
 * Design Pattern: Factory Pattern + Strategy Pattern<br>
 * <p>
 *     - Factory: Creates appropriate InputAction objects<br>
 *     - Strategy: Different actions are different strategies for handling input<br>
 * </p>
 */
public class KeyBindingManager {
    private final Map<KeyCode, InputAction> gameplayActions;
    private final Map<KeyCode, InputAction> systemActions;

    /**
     * Creates a KeyBindingManager with all default bindings.
     *
     * @param eventListener         The listener for game events
     * @param refreshCoordinator    The refresh handler for updating UI
     * @param rectangles            The brick area rectangles
     * @param brickPanel            The panel containing the brick
     * @param gamePanel             The main game panel
     * @param guiController         The GUI controller
     */
    public KeyBindingManager(
            InputEventListener eventListener,
            RefreshCoordinator refreshCoordinator,
            Rectangle[][] rectangles,
            GridPane brickPanel,
            GridPane gamePanel,
            GuiController guiController) {

        gameplayActions = new HashMap<>();
        systemActions = new HashMap<>();

        initializeGameplayBindings(eventListener, refreshCoordinator, rectangles, brickPanel, gamePanel, guiController);
        initializeSystemBindings(guiController);
    }

    /**
     * Initialises mappings for gameplay actions (only work when game is running).<br>
     * ------------------------------------IMPORTANT---------------------------------------<br>
     * Should there be any new key binds, add here.<br>
     * ------------------------------------IMPORTANT---------------------------------------<br>
     * Note to self: <br>
     * <p>
     *     In Java, lambda function is: (parameters) -> { body } <br>
     *     In JavaScript, lambda function is: "function_name" = (parameters) => function <br>
     *     In Haskell, lambda function is: \x y -> x + y <br>
     * </p>
     * this::function_name is a method reference. <br>
     */
    private void initializeGameplayBindings(
            InputEventListener eventListener,
            RefreshCoordinator refreshCoordinator,
            Rectangle[][] rectangles,
            GridPane brickPanel,
            GridPane gamePanel,
            GuiController guiController) {

        InputAction moveLeft = event -> refreshCoordinator.renderActiveBrick(
                eventListener.onLeftEvent(event),
                rectangles, brickPanel, gamePanel
        );

        InputAction moveRight = event -> refreshCoordinator.renderActiveBrick(
                eventListener.onRightEvent(event),
                rectangles, brickPanel, gamePanel
        );

        InputAction rotateClock = event -> refreshCoordinator.renderActiveBrick(
                eventListener.onRotateClock(event),
                rectangles, brickPanel, gamePanel
        );

        InputAction rotateAntiClock = event -> refreshCoordinator.renderActiveBrick(
                eventListener.onRotateAntiClock(event),
                rectangles, brickPanel, gamePanel
        );

//        InputAction moveDown = guiController::moveDown;
        InputAction moveDown = event -> refreshCoordinator.renderActiveBrick(
                eventListener.onDownEvent(event).viewData(),
                rectangles, brickPanel, gamePanel
        );

        InputAction snap = event -> refreshCoordinator.renderActiveBrick(
                eventListener.onSnapEvent(event),
                rectangles, brickPanel, gamePanel
        );

        InputAction hold = event -> eventListener.onHoldEvent();

        gameplayActions.put(KeyCode.LEFT, moveLeft);
        gameplayActions.put(KeyCode.A, moveLeft);
        gameplayActions.put(KeyCode.RIGHT, moveRight);
        gameplayActions.put(KeyCode.D, moveRight);
        gameplayActions.put(KeyCode.X, rotateClock);
        gameplayActions.put(KeyCode.Z, rotateAntiClock);
        gameplayActions.put(KeyCode.UP, rotateAntiClock);
        gameplayActions.put(KeyCode.W, rotateAntiClock);
        gameplayActions.put(KeyCode.DOWN, moveDown);
        gameplayActions.put(KeyCode.S, moveDown);
        gameplayActions.put(KeyCode.SPACE, snap);
        gameplayActions.put(KeyCode.C, hold);
    }

    /**
     * Initialises mappings for system actions (work anytime).
     */
    private void initializeSystemBindings(GuiController guiController) {
        systemActions.put(KeyCode.ESCAPE, event -> guiController.pauseGame(null));
        systemActions.put(KeyCode.N, event -> guiController.newGame(null));
    }

    /**
     * Gets the gameplay action mapped to the given key code.
     * @param keyCode The key code to look up
     * @return The mapped action, or null if none exists
     */
    public InputAction getGameplayAction(KeyCode keyCode) {
        return gameplayActions.get(keyCode);
    }

    /**
     * Gets the system action mapped to the given key code.
     * @param keyCode The key code to look up
     * @return The mapped action, or null if none exists
     */
    public InputAction getSystemAction(KeyCode keyCode) {
        return systemActions.get(keyCode);
    }

    /**
     * Gets the event type associated with a key code.<br>
     * Used for creating MoveEvent objects.<br>
     */
    public EventType getEventType(KeyCode code) {
        if (code == KeyCode.LEFT || code == KeyCode.A) return EventType.LEFT;
        if (code == KeyCode.RIGHT || code == KeyCode.D) return EventType.RIGHT;
        if (code == KeyCode.UP || code == KeyCode.W || code == KeyCode.X) return EventType.ROTATE_CLOCK;
        if (code == KeyCode.Z) return EventType.ROTATE_ANTICLOCK;
        if (code == KeyCode.DOWN || code == KeyCode.S) return EventType.DOWN;
        if (code == KeyCode.SPACE) return EventType.SNAP;
        return EventType.DOWN; // default to catch any glitches or bugs
    }
}