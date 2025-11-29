package com.comp2042.bricks.actions;

import com.comp2042.logic.detection_system.CollisionDetector;

import java.awt.Point;

/**
 * Handles all brick movement logic.
 * SOLID: Single Responsibility - Only manages brick movement
 */
public class BrickMover {

    /**
     * Attempts to move brick in specified direction
     *
     * @param gameMatrix    CurrentGameMatrix.
     * @param brickShape    Brick orientation shape.
     * @param currentOffset Current Tetromino coordinates.
     * @param dx            x-coordinate offset.
     * @param dy            y-coordinate offset.
     * @return True if move is valid, false otherwise
     */
    public static boolean tryMoveBrick(int[][] gameMatrix, int[][] brickShape,
                                       Point currentOffset, int dx, int dy) {

        Point newPosition = new Point(currentOffset);
        newPosition.translate(dx, dy);

        if (CollisionDetector.wouldCollide(gameMatrix,
                                           brickShape,
                                           (int)newPosition.getX(),
                                           (int)newPosition.getY())) {
            return false;
        }

        currentOffset.setLocation(newPosition);
        return true;
    }
}