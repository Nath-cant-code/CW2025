package com.comp2042.ui;

/**
 * Configuration for the special shape bonus feature.
 * Modify the SPECIAL_SHAPE matrix to define your own pattern.
 *
 * SOLID: Single Responsibility - Only manages special shape configuration
 */
public class SpecialShapeConfig {

    /**
     * Define your special shape here.
     * Use 0 for empty spaces, 1 for filled blocks.
     * Example: A simple 3x3 square pattern
     *
     * MODIFY THIS MATRIX TO CREATE YOUR OWN PATTERN:
     */
    public static final int[][] SPECIAL_SHAPE = {
            {1, 1, 1},
            {1, 0, 1},
            {1, 1, 1}
    };

    /**
     * The bonus points awarded for completing the special shape.
     * MODIFY THIS VALUE as desired (default: 1,000,000)
     */
    public static final int BONUS_POINTS = 1000000;

    /**
     * Message displayed when special shape is completed.
     * MODIFY THIS MESSAGE as desired
     */
    public static final String COMPLETION_MESSAGE = "CONGRATULATIONS!\nSPECIAL SHAPE BONUS!";

    /**
     * Duration to pause the game when showing the special shape (in milliseconds).
     * MODIFY THIS VALUE to control pause length (default: 2000ms = 2 seconds)
     */
    public static final int PAUSE_DURATION_MS = 2000;

    /**
     * Duration to display the completion message (in milliseconds).
     * MODIFY THIS VALUE to control message display time (default: 3000ms = 3 seconds)
     */
    public static final int MESSAGE_DURATION_MS = 3000;

    /**
     * Panel title text.
     * MODIFY THIS TEXT as desired
     */
    public static final String PANEL_TITLE = "Special Shape";
}