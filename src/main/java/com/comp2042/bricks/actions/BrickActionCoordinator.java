package com.comp2042.bricks.actions;

import com.comp2042.bricks.production.blueprints.Brick;
import com.comp2042.ui.elements.Score;
import java.awt.Point;

/**
 * Coordinates all brick actions (movement, rotation, snap, hold).<br>
 * SOLID: Single Responsibility - Coordinates brick operations<br>
 * Design Pattern: Facade - Provides unified interface to brick operations<br>
 */
public class BrickActionCoordinator {
    private final BrickRotator brickRotator;
    private final BrickSnapper snapHandler;
    private final BrickHolder holdHandler;

    public BrickActionCoordinator(BrickQueueManager queueManager, Score score) {
        this.brickRotator = new BrickRotator();
        this.snapHandler = new BrickSnapper(brickRotator, score);
        this.holdHandler = new BrickHolder(brickRotator, queueManager, new Point(4, 1));
    }

    /**
     * Attempts to move brick.
     */
    public boolean tryMove(int[][] gameMatrix, Point currentOffset, int dx, int dy) {
        return BrickMover.tryMoveBrick(
                gameMatrix,
                brickRotator.getCurrentShape(),
                currentOffset,
                dx, dy
        );
    }

    /**
     * Attempts to rotate brick.
     */
    public boolean tryRotate(RotationDirection direction, int[][] gameMatrix, Point currentOffset) {
        return brickRotator.tryRotateBrick(direction, gameMatrix, currentOffset);
    }

    /**
     * Executes snap operation.
     */
    public int executeSnap(int[][] gameMatrix, Point currentOffset) {
        return snapHandler.executeSnap(gameMatrix, currentOffset);
    }

    /**
     * Executes hold operation.
     */
    public Point executeHold(Runnable createNewBrickCallback) {
        return holdHandler.executeHold(createNewBrickCallback);
    }

    /**
     * Delegate method to BrickRotator.<br>
     * Gets current Brick object's current orientation.<br>
     * @return  Current Brick object's current orientation.
     */
    public int[][] getCurrentShape() {
        return brickRotator.getCurrentShape();
    }

    /**
     * Delegate method to BrickRotator.
     * @param brick Brick object.
     */
    public void setBrick(Brick brick) {
        brickRotator.setBrick(brick);
    }

    /**
     * Delegate method to BrickRotator.
     * @return  Brick object.
     */
    public Brick getBrick() {
        return brickRotator.getBrick();
    }
}