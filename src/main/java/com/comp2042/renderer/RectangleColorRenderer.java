package com.comp2042.renderer;

import javafx.scene.shape.Rectangle;

public class RectangleColorRenderer {
    /**
     * setRectangleData(src, dest).<br>
     * Updates the colour of each pixel of object passed as dest to match the colour of src.
     * @param color    A single pixel of board (in this class) (AKA playable area, currentGameMatrix) or a Brick-shape-object.
     * @param rectangle A single pixel displayMatrix.
     */
    public static void setRectangleColor(int color, Rectangle rectangle) {
        rectangle.setFill(ColorSelector.getFillColor(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }
}
