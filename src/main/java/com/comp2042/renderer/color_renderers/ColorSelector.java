package com.comp2042.renderer.color_renderers;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Selects color based on integer passed.
 * SOLID: SR,O/C
 * Design pattern: Decorator
 */
public class ColorSelector {
    /**
     * Sets a colour for the Brick-shape-object.
     * @param i Index to choose colour.
     * @return  Color object.
     */
    public static Paint getFillColor(int i) {
        return switch (i) {
            case 0 -> Color.TRANSPARENT;
            case 1 -> Color.AQUA;
            case 2 -> Color.BLUEVIOLET;
            case 3 -> Color.DARKGREEN;
            case 4 -> Color.YELLOW;
            case 5 -> Color.RED;
            case 6 -> Color.BEIGE;
            case 7 -> Color.BURLYWOOD;
            default -> Color.WHITE;
        };
    }
}
