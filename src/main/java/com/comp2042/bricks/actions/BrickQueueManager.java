package com.comp2042.bricks.actions;

import com.comp2042.logic.game_records.ViewData;
import com.comp2042.bricks.production.blueprints.Brick;
import com.comp2042.bricks.production.brick_generation_system.BrickGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the hold and preview brick queues.<br>
 * SOLID: Single Responsibility - Only manages brick queues<br>
 * Design Pattern: Facade - Simplifies queue management interface<br>
 */
public class BrickQueueManager {
    private Brick heldBrick = null;
    private boolean holdUsedThisTurn = false;
    private final BrickGenerator brickGenerator;

    public BrickQueueManager(BrickGenerator brickGenerator) {
        this.brickGenerator = brickGenerator;
    }

    /**
     * Get Brick object in hold panel.
     * @return  Brick object in hold panel
     */
    public Brick getHeldBrick() {
        return heldBrick;
    }

    /**
     * Inverts boolean checker.
     * @return  !holdUsedThisTurn
     */
    public boolean canUseHold() {
        return !holdUsedThisTurn;
    }

    /**
     * Called when a new Brick object is spawned.
     */
    public void resetHoldForNewTurn() {
        holdUsedThisTurn = false;
    }

    /**
     * Resets the hold state for a new game.
     * Clears the held brick and resets the hold usage flag.
     */
    public void resetHoldForNewGame() {
        heldBrick = null;
        holdUsedThisTurn = false;
    }

    /**
     * Swaps current brick with held brick.
     * @param currentBrick The brick currently in play
     * @return The brick to put into play (either held brick or null if first hold)
     */
    public Brick swapWithHeld(Brick currentBrick) {
        Brick result = heldBrick;
        heldBrick = currentBrick;
        holdUsedThisTurn = true;
        return result;
    }

    /**
     * Get the next 3 Brick objects in the queue.
     * @return  List of the 3 Brick objects next in queue.
     */
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

    /**
     * Get Brick object in queue but DO NOT POP.
     * @return Brick object in queue.
     */
    public Brick getNextBrick() {
        return brickGenerator.getNextBrick();
    }

    /**
     * POPs Brick object from queue.
     * @return  Brick object in queue.
     */
    public Brick generateBrick() {
        return brickGenerator.getBrick();
    }
}