package com.comp2042.board;

import com.comp2042.board.composite_bricks.ViewData;
import com.comp2042.bricks.Brick;
import com.comp2042.bricks.brick_generation_system.BrickGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the hold and preview brick queues.
 * SOLID: Single Responsibility - Only manages brick queues
 * Design Pattern: Facade - Simplifies queue management interface
 */
public class BrickQueueManager {
    private Brick heldBrick = null;
    private boolean holdUsedThisTurn = false;
    private final BrickGenerator brickGenerator;

    public BrickQueueManager(BrickGenerator brickGenerator) {
        this.brickGenerator = brickGenerator;
    }

    public Brick getHeldBrick() {
        return heldBrick;
    }

    public boolean canUseHold() {
        return !holdUsedThisTurn;
    }

    public void markHoldUsed() {
        holdUsedThisTurn = true;
    }

    public void resetHoldForNewTurn() {
        holdUsedThisTurn = false;
    }

    /**
     * Swaps current brick with held brick
     * @param currentBrick The brick currently in play
     * @return The brick to put into play (either held brick or null if first hold)
     */
    public Brick swapWithHeld(Brick currentBrick) {
        Brick result = heldBrick;
        heldBrick = currentBrick;
        holdUsedThisTurn = true;
        return result;
    }

    public List<ViewData> getPreviewData() {
        List<ViewData> previews = new ArrayList<>();
        List<Brick> actualQueue = brickGenerator.getUpcomingBricks();

        for (int i = 0; i < Math.min(3, actualQueue.size()); i++) {
            Brick brick = actualQueue.get(i);
            int[][] shape = brick.getShapeMatrix().getFirst();

            ViewData vd = new ViewData(shape, 0, i * 4, null);
            previews.add(vd);
        }

        return previews;
    }

    public Brick getNextBrick() {
        return brickGenerator.getNextBrick();
    }

    public Brick generateBrick() {
        return brickGenerator.getBrick();
    }
}