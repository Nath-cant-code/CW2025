package com.comp2042.ui.panels;

import com.comp2042.ui.elements.SpecialShapeConfig;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Displays the special shape pattern for the player to see.
 * SOLID: Single Responsibility - Only displays the special shape target
 */
public class SpecialShapeDisplayPanel extends VBox {
    private static final int CELL_SIZE = 20;

    public SpecialShapeDisplayPanel() {
        setAlignment(Pos.CENTER);
        setSpacing(10);

        Label titleLabel = new Label(SpecialShapeConfig.PANEL_TITLE);
        titleLabel.getStyleClass().add("specialShapeTitle");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(2);
        gridPane.setVgap(2);

        int[][] pattern = SpecialShapeConfig.SPECIAL_SHAPE;

        for (int y = 0; y < pattern.length; y++) {
            for (int x = 0; x < pattern[0].length; x++) {
                Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);

                if (pattern[y][x] == 1) {
                    rect.setFill(Color.GOLD);
                    rect.setStroke(Color.DARKGOLDENROD);
                } else {
                    rect.setFill(Color.TRANSPARENT);
                    rect.setStroke(Color.GRAY);
                }

                rect.setStrokeWidth(1);
                rect.setArcHeight(5);
                rect.setArcWidth(5);

                gridPane.add(rect, x, y);
            }
        }

        getChildren().addAll(titleLabel, gridPane);
    }
}