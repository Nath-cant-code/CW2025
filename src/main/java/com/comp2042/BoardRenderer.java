package com.comp2042;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Creates visual playable area (displayMatrix) from boardMatrix (AKA currentGameMatrix).
 */
public class BoardRenderer {
    public Rectangle[][] displayMatrix;

    /**
     *
     * @param gamePanel     gamePanel.
     * @param boardMatrix   Playable area matrix.
     * @return              Playable area matrix.
     */
    public Rectangle[][] createPlayableAreaMatrix (GridPane gamePanel, int[][] boardMatrix) {
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];

        for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                javafx.scene.shape.Rectangle rectangle = new javafx.scene.shape.Rectangle(GuiController.BRICK_SIZE, GuiController.BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i - 2);
            }
        }
        return displayMatrix;
    }
}
