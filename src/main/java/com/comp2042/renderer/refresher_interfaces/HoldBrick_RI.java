package com.comp2042.renderer.refresher_interfaces;

import com.comp2042.bricks.production.blueprints.AbstractBrick;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

/**
 * Renders the hold panel showing the held brick.
 */
public interface HoldBrick_RI {
    /**
     * Updates the hold panel to show the currently held brick.
     *
     * @param holdBrick  The brick being held (or null)
     * @param holdMatrix The hold panel's UI rectangles
     * @param holdPanel  The hold panel grid
     */
    void refreshHoldBrick(AbstractBrick holdBrick, Rectangle[][] holdMatrix, GridPane holdPanel);
}
