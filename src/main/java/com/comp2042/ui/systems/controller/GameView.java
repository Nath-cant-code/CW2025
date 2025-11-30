package com.comp2042.ui.systems.controller;

import com.comp2042.logic.records.ViewData;
import com.comp2042.input.event_listener.InputEventListener;
import javafx.beans.property.IntegerProperty;
import javafx.scene.shape.Rectangle;

/**
 * Abstraction for the game view/UI layer.<br>
 * <p>
 * Design Pattern: Dependency Inversion Principle (DIP)<br>
 * - High-level EventListener depends on this abstraction<br>
 * - Low-level GuiController implements this abstraction<br>
 * - Both depend on the abstraction, not on each other<br>
 * </p>
 * <p>
 * Benefits:<br>
 * - EventListener can work with ANY UI implementation<br>
 * - Can test EventListener without JavaFX<br>
 * - Can create different UIs (console, web, mobile) easily<br>
 * - True separation of concerns<br>
 * </p>
 */
public interface GameView {

    /**
     * Initialises the game view with the initial board state.
     * @param boardMatrix The initial board matrix
     * @param initialBrick The first brick to display
     */
    void initGameView(int[][] boardMatrix, ViewData initialBrick);

    /**
     * Sets the event listener for user input.
     * @param listener The input event listener
     */
    void setEventListener(InputEventListener listener);

    /**
     * Binds the score display to the score property.
     * @param scoreProperty The score property to bind to
     */
    void bindScore(IntegerProperty scoreProperty);

    /**
     * Shows a notification for cleared rows.
     * @param points The points earned
     */
    void showClearRowNotification(int points);

    /**
     * Notifies the view that the game is over.
     */
    void notifyGameOver();

    /**
     * Gets the display matrix for rendering.
     * @return The rectangle matrix representing the display
     */
    Rectangle[][] getDisplayMatrix();

    void refreshActiveBrick (ViewData viewData);

    /**
     * Updates the background rendering.
     * @param boardMatrix The current board state
     */
    void refreshBackground (int[][] boardMatrix);

    void refreshHoldBrick ();

    void refreshPreviewPanel ();

    void notifyLevelUp(int newLevel);

    void updateFallSpeed(int speedMs);

    void bindLevel(IntegerProperty levelProperty);

    void handleSpecialShapeCompletion();

    void hideSpecialShapeDisplay();

    boolean isSpecialShapeDisplayVisible();
}