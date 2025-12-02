package com.comp2042.renderer.refresher_interfaces;

import com.comp2042.logic.game_records.ViewData;
import javafx.scene.shape.Rectangle;

import java.util.List;

/**
 * Renders the preview of upcoming bricks.<br>
 * SOLID: Interface Segregation, Dependency Inversion<br>
 * Design Pattern: Template: Defines skeleton of refresh method<br>
 */
public interface PreviewBricks_RI {
    /**
     * Updates the next bricks preview panel.
     * @param previews The list of upcoming brick data
     * @param nextMatrix The preview panel's UI rectangles
     */
    void refreshPreviewBricks(List<ViewData> previews, Rectangle[][] nextMatrix);
}