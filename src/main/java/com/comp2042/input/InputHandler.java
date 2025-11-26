package com.comp2042.input;

import com.comp2042.input.event_controllers.InputEventListener;
import com.comp2042.ui.ui_systems.GuiController;
import com.comp2042.system_events.EventSource;
import com.comp2042.system_events.MoveEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

/**
 * This class was created to separate the user input handling from the initialise() method in GuiController.<br>
 * ----------------------------------REFACTORED----------------------------------<br>
 * Handles key events using the map-based system.<br>
 * Summary:<br>
 * SOLID principles complied:<br>
 * <p>
 *     - SRP: KeyBindingManager<br>
 *     - O/C: KeyBindingManager, InputAction<br>
 * </p>
 * Design Patterns:<br>
 * <p>
 *     - Factory (Creational) : KeyBindingManager<br>
 *     - Strategy (Behavioural) : KeyBindingManager<br>
 *     - Command (Behavioural) : InputAction<br>
 * </p>
 * ----------------------------------REFACTORED----------------------------------<br>
 * NOTES:<br>
 * <p>
 * - Removed the obnoxious massive if-else chains<br>
 * - Easy to add new key bindings<br>
 */
public class InputHandler {
    private final GuiController gc;
    private final KeyBindingManager keyBindingManager;

    /**
     * Creates an InputHandler with default key bindings.
     */
    public InputHandler(GuiController gc,
                        InputEventListener eventListener,
                        Rectangle[][] rectangles,
                        GridPane brickPanel,
                        GridPane gamePanel) {
        this.gc = gc;

        // Create key binding manager with all the necessary dependencies
        this.keyBindingManager = new KeyBindingManager(
                eventListener,
                gc.refreshCoordinator,
                rectangles,
                brickPanel,
                gamePanel,
                gc
        );
    }

    /**
     * this::function_name is a method reference. <br>
     */
    public void setKeyListener() {
        gc.gamePanel.setOnKeyPressed(this::handleKeyEvent);
    }

    /**
     * 1st if statement is strictly for N (new game) or ESC (pause & resume game) only.<br>
     *
     * 2nd if statement checks if game is paused or over before allowing the player's inputs
     * to be passed for processing. <br>
     * If the game is in either state,
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
        KeyCode code = keyEvent.getCode();

        // System actions work anytime (pause, new game)
        InputAction systemAction = keyBindingManager.getSystemAction(code);
        if (systemAction != null) {
            systemAction.execute(new MoveEvent(
                    keyBindingManager.getEventType(code),
                    EventSource.USER
            ));
            keyEvent.consume();
            return;
        }

        // Gameplay actions only work when game is running
        if (gc.getGameStateManager().canProcessInput()) {
            InputAction gameplayAction = keyBindingManager.getGameplayAction(code);
            if (gameplayAction != null) {
                gameplayAction.execute(new MoveEvent(
                        keyBindingManager.getEventType(code),
                        EventSource.USER
                ));
                keyEvent.consume();
            }
        }
    }
}
