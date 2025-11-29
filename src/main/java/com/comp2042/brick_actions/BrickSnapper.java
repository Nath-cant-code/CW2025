package com.comp2042.brick_actions;

import com.comp2042.board.detection_system.CollisionDetector;
import com.comp2042.ui.Score;
import java.awt.Point;

/**
 * Handles brick snap (hard drop) logic.<br>
 * SOLID: Single Responsibility - Only manages snap logic<br>
 * Design Pattern: Strategy - Snap behavior can be modified independently<br>
 */
public class BrickSnapper {
    private final BrickRotator brickRotator;
    private final Score score;

    public BrickSnapper(BrickRotator brickRotator, Score score) {
        this.brickRotator = brickRotator;
        this.score = score;
    }

    /**
     * Executes snap operation.
     * @param gameMatrix Current game matrix
     * @param currentOffset Current brick position
     * @return New Y position after snap
     */
    public int executeSnap(int[][] gameMatrix, Point currentOffset) {
        int targetY = findSnapPosition(
                gameMatrix,
                brickRotator.getCurrentShape(),
                (int) currentOffset.getX(),
                (int) currentOffset.getY()
        );

//        score multiplier for hard dropping
        score.add((targetY - currentOffset.y) * 5);

        return targetY;
    }

    /**
     * Iteratively checks for collisions and returns the position of collision.
     * @param board Current game matrix
     * @param shape Current orientation of Brick object
     * @param x Current x-coordinates of Brick object
     * @param y Current y-coordinates of Brick object
     * @return  Y coordinates of position of collision
     */
    public static int findSnapPosition(int[][] board, int[][] shape, int x, int y) {
        int targetY = y;
        while (!CollisionDetector.wouldCollide(board, shape, x, targetY + 1)) {
            targetY++;
        }
        return targetY;
    }
}