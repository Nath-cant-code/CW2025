package com.comp2042.renderer;

import com.comp2042.board.composite_bricks.ViewData;
import com.comp2042.bricks.AbstractBrick;
import com.comp2042.renderer.refresher_interfaces.*;
import com.comp2042.renderer.concrete_refreshers.*;
import com.comp2042.ui.ui_systems.GameStateManager;

import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import java.util.List;

/**
 * Coordinates all rendering operations using the specialized renderers.
 *
 * Design Pattern: Facade Pattern
 * - Provides a unified interface to a set of interfaces (renderers)
 * - Simplifies the subsystem for clients
 *
 * SOLID: Dependency Inversion
 * - Depends on renderer interfaces, not concrete implementations
 * - Easy to swap renderer implementations
 */
public class RefreshCoordinator {
    private final GameBackground_RI bgRenderer;
    private final ActiveBrick_RI fbRenderer;
    private final HoldBrick_RI holdRenderer;
    private final PreviewBricks_RI previewRenderer;
    private final GameStateManager gameStateManager;

    /**
     * Creates a RenderingCoordinator with default renderers.
     * @param brickSize The size of brick cells in pixels
     * @param gameStateManager The game state manager
     */
    public RefreshCoordinator(int brickSize, GameStateManager gameStateManager) {
        this.bgRenderer = new RefreshGameBackground();
        this.fbRenderer = new RefreshActiveBrick(brickSize);
        this.holdRenderer = new RefreshHoldPanel();
        this.previewRenderer = new RefreshPreviewBricks();
        this.gameStateManager = gameStateManager;
    }

    /**
     * Renders the active falling brick (only if game is not paused).
     */
    public void renderActiveBrick(ViewData brick, Rectangle[][] rectangles,
                                  GridPane brickPanel, GridPane gamePanel) {
        if (!gameStateManager.isPaused()) {
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