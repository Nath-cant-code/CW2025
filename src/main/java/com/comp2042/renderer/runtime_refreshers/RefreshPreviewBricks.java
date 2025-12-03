package com.comp2042.renderer.runtime_refreshers;

import com.comp2042.logic.game_records.ViewData;
import com.comp2042.renderer.color_renderers.ColorSelector;
import com.comp2042.renderer.refresher_interfaces.PreviewBricks_RI;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

/**
 * Renders the next bricks preview.<br>
 * SOLID: Single Responsibility<br>
 * Design Pattern: Strategy: Encapsulates refreshing logic,
 * allowing this class to act independently of other refresh classes<br>
 */
public class RefreshPreviewBricks implements PreviewBricks_RI {

    @Override
    public void refreshPreviewBricks(List<ViewData> previews, Rectangle[][] nextMatrix) {
        // Clear preview area
        for (Rectangle[] matrix : nextMatrix) {
            for (int j = 0; j < nextMatrix[0].length; j++) {
                matrix[j].setFill(Color.TRANSPARENT);
            }
        }

//        Render each preview brick
        int index = 0;
        for (ViewData vd : previews) {
            int[][] shape = vd.brickData();
            int offsetY = index * 4; // Each brick in its own 4-row section
            int offsetX = 0;

//            Inversion of y and x loops needed to prevent preview panel from showing mirrored images
//            cannot be applied for hold panel
            for (int y = 0; y < shape.length; y++) {
                for (int x = 0; x < shape[0].length; x++) {
                    if (shape[y][x] != 0) {
                        Rectangle r = nextMatrix[offsetY + y][offsetX + x];
                        r.setFill(ColorSelector.getFillColor(shape[y][x]));
                    }
                }
            }
            index++;
        }
    }
}