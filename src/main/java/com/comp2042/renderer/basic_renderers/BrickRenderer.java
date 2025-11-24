package com.comp2042.renderer.basic_renderers;

import com.comp2042.renderer.color_renderers.ColorSelector;
import com.comp2042.ui.ui_systems.GuiController;
import com.comp2042.board.composite_bricks.ViewData;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

/**
 * This class is to separate "Brick object box matrix" object creation from GuiController.java class.
 */
public class BrickRenderer {
    /**
     * Create a box area (rectangles) in which the current Brick-shape-object resides in until it merges with the playable area.
     * @param brickPanel    brickPanel.
     * @param brick         Brick object info.
     * @return              Box area matrix.
     */
    public Rectangle[][] createBrickAreaMatrix (GridPane brickPanel, ViewData brick) {
        Rectangle[][] rectangles = new Rectangle[brick.brickData().length][brick.brickData()[0].length];

        for (int i = 0; i < brick.brickData().length; i++) {
            for (int j = 0; j < brick.brickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(GuiController.BRICK_SIZE, GuiController.BRICK_SIZE);
                rectangle.setFill(ColorSelector.getFillColor(brick.brickData()[i][j]));
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }
        return rectangles;
    }
}
