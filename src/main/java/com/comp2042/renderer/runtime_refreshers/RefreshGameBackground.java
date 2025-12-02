package com.comp2042.renderer.runtime_refreshers;

import com.comp2042.renderer.color_renderers.RectangleColorRenderer;
import com.comp2042.renderer.refresher_interfaces.GameBackground_RI;
import javafx.scene.shape.Rectangle;

/**
 * Renders the game board background.<br>
 * SOLID: Single Responsibility<br>
 * Design Pattern: Strategy: Encapsulates refreshing logic,
 * allowing this class to act independently of other refresh classes<br>
 */
public class RefreshGameBackground implements GameBackground_RI {

    @Override
    public void refreshBackground(int[][] board, Rectangle[][] displayMatrix) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                RectangleColorRenderer.setRectangleColor(board[i][j], displayMatrix[i][j]);
            }
        }
    }
}