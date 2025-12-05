package com.comp2042.logic.detection_system;

import com.comp2042.ui.elements.SpecialShapeConfig;

import java.awt.Point;

/**
 * Detects if a special shape pattern exists in the game board.
 * SOLID: Single Responsibility - Only detects special shape patterns
 * Design Pattern: Strategy - Detection algorithm can be changed independently
 */
public class SpecialShapeDetector {

    private boolean shapeCompleted = false;

    /**
     * Scans the entire board for the special shape pattern. <br>
     * The algorithm works by evaluating each pixel as the top left pixel of the special combination shape.<br>
     * What this means is that for every point, the algorithm will check an area equal to the size of the special combination shape
     * with the current point as the top left pixel of the special shape.<br>
     * If the special combination shape exists, algorithm returns the pixel as the starting point of the special shape,
     * which will correspond as the top left pixel of the special shape.<br>
     * The efficiency of the algorithm is enhanced by subtracting the size of the area of the special combination shape
     * from the search range. <br>
     * This is because if the space is lesser than the special combination shape's area,
     * then the special shape cannot exist with the current point as the top left pixel.
     * @param boardMatrix The current game board state
     * @return Point containing the top-left position if found, null otherwise
     */
    public Point detectSpecialShape(int[][] boardMatrix) {
        if (shapeCompleted) {
            return null; // Already completed, don't detect again
        }

        int[][] pattern = SpecialShapeConfig.SPECIAL_SHAPE;
        int patternHeight = pattern.length;
        int patternWidth = pattern[0].length;

//        Start from row 2 (skip the spawn area)
//        every coord is a starting point
        for (int boardY = 2; boardY <= boardMatrix.length - patternHeight; boardY++) {
            for (int boardX = 0; boardX <= boardMatrix[0].length - patternWidth; boardX++) {
                if (matchesPattern(boardMatrix, pattern, boardX, boardY)) {
                    return new Point(boardX, boardY);
                }
            }
        }

        return null;
    }

    /**
     * Checks if the pattern matches at the given position.
     * @param board The game board
     * @param pattern The pattern to match
     * @param startX Starting X position on board
     * @param startY Starting Y position on board
     * @return true if pattern matches
     */
    private boolean matchesPattern(int[][] board, int[][] pattern, int startX, int startY) {
        for (int py = 0; py < pattern.length; py++) {
            for (int px = 0; px < pattern[0].length; px++) {
                int boardValue = board[startY + py][startX + px];
                int patternValue = pattern[py][px];

//                Pattern expects filled (1) but board is empty (0)
                if (patternValue == 1 && boardValue == 0) {
                    return false;
                }
//                Pattern expects empty (0) but board is filled (non-zero)
                if (patternValue == 0 && boardValue != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Marks the special shape as completed.
     */
    public void markCompleted() {
        this.shapeCompleted = true;
    }

    /**
     * Checks if the special shape has been completed.
     */
    public boolean isCompleted() {
        return shapeCompleted;
    }

    /**
     * Resets the completion status (for new games).
     */
    public void reset() {
        this.shapeCompleted = false;
    }
}