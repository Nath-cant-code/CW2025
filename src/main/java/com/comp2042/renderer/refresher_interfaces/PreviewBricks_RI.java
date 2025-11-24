package com.comp2042.renderer.refresher_interfaces;

import com.comp2042.board.composite_bricks.ViewData;
import javafx.scene.shape.Rectangle;

import java.util.List;

/**
 * Renders the preview of upcoming bricks.
 */
public interface PreviewBricks_RI {
    /**
     * Updates the next bricks preview panel.
     * @param previews The list of upcoming brick data
     * @param nextMatrix The preview panel's UI rectangles
     */
    void refreshPreviewBricks(List<ViewData> previews, Rectangle[][] nextMatrix);
}