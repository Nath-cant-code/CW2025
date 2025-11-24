package com.comp2042.renderer.concrete_refreshers;

import com.comp2042.bricks.AbstractBrick;
import com.comp2042.renderer.RectangleColorRenderer;
import com.comp2042.renderer.refresher_interfaces.HoldBrick_RI;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Renders the hold panel.
 */
public class RefreshHoldPanel implements HoldBrick_RI {

    @Override
    public void refreshHoldBrick(AbstractBrick holdBrick, Rectangle[][] holdMatrix, GridPane holdPanel) {
        // Clear the hold panel
        for (int i = 0; i < holdMatrix.length; i++) {
            for (int j = 0; j < holdMatrix[i].length; j++) {
                holdMatrix[i][j].setFill(Color.TRANSPARENT);
            }
        }

        // If no brick is held, we're done
        if (holdBrick == null) return;

        // Render the held brick centered
        int[][] shape = holdBrick.getShapeMatrix().getFirst();
        int shapeWidth = shape.length;
        int shapeHeight = shape[0].length;

        int panelWidth = holdMatrix.length;
        int panelHeight = holdMatrix[0].length;

        int offsetX = (panelWidth - shapeWidth) / 2;
        int offsetY = (panelHeight - shapeHeight) / 2;

        for (int x = 0; x < shapeWidth; x++) {
            for (int y = 0; y < shapeHeight; y++) {
                int color = shape[x][y];
                if (color != 0) {
                    int drawX = x + offsetX;
                    int drawY = y + offsetY;
                    if (drawX >= 0 && drawX < panelWidth &&
                            drawY >= 0 && drawY < panelHeight) {
                        RectangleColorRenderer.setRectangleColor(color, holdMatrix[drawX][drawY]);
                    }
                }
            }
        }
    }
}
