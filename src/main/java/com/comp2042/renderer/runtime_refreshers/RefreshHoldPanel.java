package com.comp2042.renderer.runtime_refreshers;

import com.comp2042.bricks.production.blueprints.AbstractBrick;
import com.comp2042.renderer.color_renderers.RectangleColorRenderer;
import com.comp2042.renderer.refresher_interfaces.HoldBrick_RI;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Renders the hold panel.<br>
 * SOLID: Single Responsibility<br>
 * Design Pattern: Strategy: Encapsulates refreshing logic,
 * allowing this class to act independently of other refresh classes<br>
 */
public class RefreshHoldPanel implements HoldBrick_RI {

    @Override
    public void refreshHoldBrick(AbstractBrick holdBrick, Rectangle[][] holdMatrix, GridPane holdPanel) {
//        Clear the hold panel
        for (Rectangle[] matrix : holdMatrix) {
            for (Rectangle rectangle : matrix) {
                rectangle.setFill(Color.TRANSPARENT);
            }
        }

//        If no brick is held, we're done
        if (holdBrick == null) return;

//        Render the held brick centered
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
//                        For some reason if i do holdMatrix[drawX][drawY] the displayed Brick is a mirror image
//                        Only applies to this rendering and not Preview panel rendering
                        RectangleColorRenderer.setRectangleColor(color, holdMatrix[drawY][drawX]);
                    }
                }
            }
        }
    }
}
