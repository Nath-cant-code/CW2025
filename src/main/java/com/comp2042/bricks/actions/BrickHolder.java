package com.comp2042.bricks.actions;

import com.comp2042.bricks.production.blueprints.Brick;
import java.awt.Point;

/**
 * Handles brick hold logic.<br>
 * SOLID: Single Responsibility - Only manages hold logic<br>
 * Design Pattern: Strategy - Hold behavior can be modified<br>
 */
public class BrickHolder {
    private final BrickRotator brickRotator;
    private final BrickQueueManager queueManager;
    private final Point spawnPoint;

    public BrickHolder(BrickRotator brickRotator, BrickQueueManager queueManager, Point spawnPoint) {
        this.brickRotator = brickRotator;
        this.queueManager = queueManager;
        this.spawnPoint = new Point(spawnPoint);
    }

    /**
     * Executes hold operation.
     * @param createNewBrickCallback Callback to create new brick if needed
     * @return New current offset after hold
     */
    public Point executeHold(Runnable createNewBrickCallback) {
//        check if hold is allowed during turn
        if (!queueManager.canUseHold()) { return null; }

        Brick currentBrick = brickRotator.getBrick();
        Brick swappedBrick = queueManager.swapWithHeld(currentBrick);

        if (swappedBrick == null) { createNewBrickCallback.run(); }
        else { brickRotator.setBrick(swappedBrick); }
        return new Point(spawnPoint);
    }
}