package com.comp2042;

import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

/**
 * This class is to separate "Brick object box matrix" object creation from GuiController.java class.
 */
public class BrickRenderer {
    public Rectangle[][] rectangles;

    /**
     * Create a box area (rectangles) in which the current Brick-shape-object resides in until it merges with the playable area.
     * @param brickPanel
     * @param brick
     * @return
     */
    public Rectangle[][] createBrickAreaMatrix (GridPane brickPanel, ViewData brick) {
        rectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];

        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(GuiController.BRICK_SIZE, GuiController.BRICK_SIZE);
                rectangle.setFill(GuiController.getFillColor(brick.getBrickData()[i][j]));
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }
        return rectangles;
    }
}
