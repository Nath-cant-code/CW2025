package com.comp2042.renderer.runtime_refreshers;

import com.comp2042.logic.records.ViewData;
import com.comp2042.bricks.production.blueprints.AbstractBrick;
import com.comp2042.renderer.refresher_interfaces.*;
import com.comp2042.ui.systems.managers.GameStateProperty;

import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import java.util.List;

/**
 * ----------------------------REFACTORING DONE----------------------------<br>
 * SOLID: Interface Segregation > refresher_interfaces > implemented by > concrete_refreshers<br>
 * Design pattern: Facade > this clas acts as a wrapper class hiding the logic
 * of all the refresh activity methods.<br>
 * ----------------------------REFACTORING DONE----------------------------<br>
 * Coordinates all rendering operations using the specialized renderers.<br>
 * <p>
 * Design Pattern: Facade Pattern<br>
 * - Provides a unified interface to a set of interfaces (renderers)<br>
 * - Simplifies the subsystem for clients<br>
 * </p>
 * <p>
 * SOLID: Dependency Inversion<br>
 * - Depends on renderer interfaces, not concrete implementations<br>
 * - Easy to swap renderer implementations<br>
 * </p>
 */
public class RefreshCoordinator {
    private final GameBackground_RI bgRenderer;
    private final ActiveBrick_RI fbRenderer;
    private final HoldBrick_RI holdRenderer;
    private final PreviewBricks_RI previewRenderer;
    private final GameStateProperty gameStateProperty;

    /**
     * Creates a RenderingCoordinator with default renderers.
     * @param brickSize The size of brick cells in pixels
     * @param gameStateProperty The game state manager
     */
    public RefreshCoordinator(int brickSize, GameStateProperty gameStateProperty) {
        this.bgRenderer = new RefreshGameBackground();
        this.fbRenderer = new RefreshActiveBrick(brickSize);
        this.holdRenderer = new RefreshHoldPanel();
        this.previewRenderer = new RefreshPreviewBricks();
        this.gameStateProperty = gameStateProperty;
    }

    /**
     * Renders the active falling brick (only if game is not paused).
     */
    public void renderActiveBrick(ViewData brick, Rectangle[][] rectangles,
                                  GridPane brickPanel, GridPane gamePanel) {
        if (!gameStateProperty.isPaused()) {
            fbRenderer.refreshActiveBrick(brick, rectangles, brickPanel, gamePanel);
        }
    }

    /**
     * Renders the game board background.
     */
    public void renderBackground(int[][] board, Rectangle[][] displayMatrix) {
        bgRenderer.refreshBackground(board, displayMatrix);
    }

    /**
     * Renders the hold panel.
     */
    public void renderHoldBrick(AbstractBrick holdBrick, Rectangle[][] holdMatrix, GridPane holdPanel) {
        holdRenderer.refreshHoldBrick(holdBrick, holdMatrix, holdPanel);
    }

    /**
     * Renders the next bricks preview.
     */
    public void renderNextBricks(List<ViewData> previews, Rectangle[][] nextMatrix) {
        previewRenderer.refreshPreviewBricks(previews, nextMatrix);
    }
}