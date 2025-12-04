package com.comp2042.ui.elements;

/**
 * Configuration for the special shape bonus feature.<br>
 * SOLID: Single Responsibility - Only manages special shape configuration<br>
 */
public class SpecialShapeConfig {

    /**
     * Define special combination shape here.<br>
     * 0 for empty spaces, 1 for filled blocks.<br>
     */
    public static final int[][] SPECIAL_SHAPE = {
            {1, 1, 1},
            {1, 0, 1},
            {1, 1, 1}
//            tried to spell DMS (Developing Maintainable Software)
//            {1, 1, 1, 0},
//            {1, 0, 1, 1},
//            {1, 0, 1, 1},
//            {1, 1, 1, 0}
    };

    /**
     * The bonus points awarded for completing the special shape.
     */
    public static final int BONUS_POINTS = 109833163;

    /**
     * Message displayed when special shape is completed.
     */
    public static final String COMPLETION_MESSAGE =
            "SPECIAL SHAPE BONUS!\n" +
            "Thanks for playing my game!\n" +
            "- Nath";

    /**
     * Duration (ms) to pause the game when showing the special shape.
     */
    public static final int PAUSE_DURATION_MS = 2000;

    /**
     * Duration (ms) to display the completion message.
     */
    public static final int MESSAGE_DURATION_MS = 3000;

    /**
     * Panel title text.
     */
    public static final String PANEL_TITLE = "Special Shape";
}