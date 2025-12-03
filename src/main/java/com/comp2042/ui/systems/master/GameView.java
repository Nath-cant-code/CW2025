package com.comp2042.ui.systems.master;

import com.comp2042.logic.engine.Board;
import com.comp2042.logic.game_records.ViewData;
import com.comp2042.input.event_listener.InputEventListener;
import com.comp2042.renderer.runtime_refreshers.RefreshCoordinator;
import com.comp2042.ui.systems.managers.GameStateProperty;
import javafx.beans.property.IntegerProperty;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

/**
 * Abstraction for the game view/UI layer.<br>
 * <p>
 * SOLID: Dependency Inversion Principle (DIP)<br>
 * - High-level EventListener depends on this abstraction<br>
 * - Low-level GUIController implements this abstraction<br>
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

    void initGameView(int[][] boardMatrix, ViewData initialBrick);

    void setBoard(Board board);

    void setEventListener(InputEventListener listener);

    GameStateProperty getGameStateManager();

    Rectangle[][] getDisplayMatrix();

    RefreshCoordinator getRefreshCoordinator();

    GridPane getGamePanel();

    void bindScore(IntegerProperty scoreProperty);

    void showClearRowNotification(int points);

    void newGame(ActionEvent actionEvent);

    void pauseGame (ActionEvent actionEvent);

    void gameOver();

    void refreshActiveBrick (ViewData viewData);

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