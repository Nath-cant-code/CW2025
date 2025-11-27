package com.comp2042.board;

import java.awt.Point;

/**
 * Handles all brick movement operations.
 * SOLID: Single Responsibility - Only manages brick movement
 * Design Pattern: Strategy - Different movement strategies can be implemented
 */
public class BrickMovementController {

    /**
     * Attempts to move brick in specified direction
     * @param gameMatrix    CurrentGameMatrix.
     * @param brickShape    Brick orientation shape.
     * @param currentOffset Current Tetromino coordinates.
     * @param dx            x-coordinate offset.
     * @param dy            y-coordinate offset.
     * @return true if movement was successful
     */
    public static boolean tryMoveBrick(int[][] gameMatrix, int[][] brickShape,
                                Point currentOffset, int dx, int dy) {
        Point newPosition = new Point(currentOffset);
        newPosition.translate(dx, dy);

        if (CollisionDetector.wouldCollide(gameMatrix, brickShape,
                (int)newPosition.getX(),
                (int)newPosition.getY())) {
            return false;
        }

        currentOffset.setLocation(newPosition);
        return true;
    }

    public static int findSnapPosition(int[][] board, int[][] shape, int x, int y) {
        int targetY = y;
        while (!CollisionDetector.wouldCollide(board, shape, x, targetY + 1)) {
            targetY++;
        }
        return targetY;
    }
}