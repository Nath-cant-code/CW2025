package com.comp2042.renderer.refresher_interfaces;

import com.comp2042.board.composite_bricks.ViewData;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

/**
 * Renders the active falling brick.
 */
public interface ActiveBrick_RI {
    /**
     * Updates the position and appearance of the currently falling brick.
     *
     * @param brick      The brick data to render
     * @param rectangles The brick's UI rectangles
     * @param brickPanel The panel containing the brick
     * @param gamePanel  The main game panel
     */
    void refreshActiveBrick(ViewData brick, Rectangle[][] rectangles,
                            GridPane brickPanel, GridPane gamePanel);
}
