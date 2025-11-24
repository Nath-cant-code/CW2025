package com.comp2042.renderer.concrete_refreshers;

import com.comp2042.board.composite_bricks.ViewData;
import com.comp2042.renderer.color_renderers.ColorSelector;
import com.comp2042.renderer.refresher_interfaces.PreviewBricks_RI;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

/**
 * Concrete implementations of renderer interfaces.
 * Each class has a single, focused responsibility.
 */

/**
 * Renders the next bricks preview.
 */
public class RefreshPreviewBricks implements PreviewBricks_RI {

    @Override
    public void refreshPreviewBricks(List<ViewData> previews, Rectangle[][] nextMatrix) {
        // Clear preview area
        for (int i = 0; i < nextMatrix.length; i++) {
            for (int j = 0; j < nextMatrix[0].length; j++) {
                nextMatrix[i][j].setFill(Color.TRANSPARENT);
            }
        }

        // Render each preview brick
        int index = 0;
        for (ViewData vd : previews) {
            int[][] shape = vd.brickData();
            int offsetY = index * 4; // Each brick in its own 4-row section
            int offsetX = 0;

            for (int x = 0; x < shape.length; x++) {
                for (int y = 0; y < shape[0].length; y++) {
                    if (shape[x][y] != 0) {
                        Rectangle r = nextMatrix[offsetY + y][offsetX + x];
                        r.setFill(ColorSelector.getFillColor(shape[x][y]));
                    }
                }
            }
            index++;
        }
    }
}