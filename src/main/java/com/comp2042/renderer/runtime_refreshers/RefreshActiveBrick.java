package com.comp2042.renderer.runtime_refreshers;

import com.comp2042.logic.game_records.ViewData;
import com.comp2042.renderer.color_renderers.RectangleColorRenderer;
import com.comp2042.renderer.refresher_interfaces.ActiveBrick_RI;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

/**
 * Renders the currently falling brick.<br>
 * SOLID: Single Responsibility<br>
 * Design Pattern: Strategy: Encapsulates refreshing logic,
 * allowing this class to act independently of other refresh classes<br>
 */
public class RefreshActiveBrick implements ActiveBrick_RI {
    private final int brickSize;

    public RefreshActiveBrick(int brickSize) {
        this.brickSize = brickSize;
    }

    @Override
    public void refreshActiveBrick(ViewData brick, Rectangle[][] rectangles,
                                   GridPane brickPanel, GridPane gamePanel) {
//        Update position
        brickPanel.setLayoutX(gamePanel.getLayoutX() +
                brick.xPosition() * brickPanel.getVgap() +
                brick.xPosition() * brickSize);
        brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() +
                brick.yPosition() * brickPanel.getHgap() +
                brick.yPosition() * brickSize);

//        Update colors
        int[][] brickData = brick.brickData();
        for (int i = 0; i < brickData.length; i++) {
            for (int j = 0; j < brickData[i].length; j++) {
                RectangleColorRenderer.setRectangleColor(brick.brickData()[i][j], rectangles[i][j]);
            }
        }
    }
}
