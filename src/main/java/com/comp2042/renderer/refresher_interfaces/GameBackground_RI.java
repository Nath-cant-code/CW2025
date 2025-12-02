package com.comp2042.renderer.refresher_interfaces;

import javafx.scene.shape.Rectangle;

/**
 * Renders the main game board background.<br>
 * SOLID: Interface Segregation, Dependency Inversion<br>
 * Design Pattern: Template: Defines skeleton of refresh method<br>
 */
public interface GameBackground_RI {
    /**
     * Updates the game board display to match the current game state.
     *
     * @param board         The board matrix to render
     * @param displayMatrix The UI rectangles to update
     */
    void refreshBackground(int[][] board, Rectangle[][] displayMatrix);
}
