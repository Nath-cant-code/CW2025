package com.comp2042.ui.systems.initializers;

import com.comp2042.logic.records.ViewData;
import com.comp2042.renderer.basic_renderers.BrickRenderer;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Initializes UI panels (hold, preview).
 * SOLID: Single Responsibility - Only initializes panels
 */
public class PanelInitialiser {
    private static final int BRICK_SIZE = 20;

    public Rectangle[][] initializeHoldPanel(GridPane holdPanel) {
        int size = 4;
        Rectangle[][] holdMatrix = new Rectangle[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Rectangle r = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                r.setFill(Color.TRANSPARENT);
                holdMatrix[i][j] = r;
                holdPanel.add(r, i, j);
            }
        }

        return holdMatrix;
    }

    public Rectangle[][] initializePreviewPanel(GridPane nextPanel) {
        BrickRenderer renderer = new BrickRenderer();

        int rows = 12;
        int cols = 4;
        int[][] emptyMatrix = new int[rows][cols];

        ViewData emptyView = new ViewData(
                emptyMatrix,
                0,
                0,
                emptyMatrix
        );

        return renderer.createBrickAreaMatrix(nextPanel, emptyView);
    }
}