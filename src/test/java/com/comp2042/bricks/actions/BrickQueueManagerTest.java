package com.comp2042.bricks.actions;

import com.comp2042.bricks.production.blueprints.Brick;
import com.comp2042.bricks.production.brick_generation_system.RandomBrickGenerator;
import com.comp2042.bricks.production.brick_shapes.IBrick;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BrickQueueManagerTest {

    private BrickQueueManager queueManager;

    @BeforeEach
    void setUp() {
        queueManager = new BrickQueueManager(new RandomBrickGenerator());
    }

    @Test
//    ensures hold panel is null when initialised
    void testGetHeldBrick_InitiallyNull() {
        assertNull(queueManager.getHeldBrick());
    }

    @Test
//    ensures hold flag is true at initialisation
    void testCanUseHold_InitiallyTrue() {
        assertTrue(queueManager.canUseHold());
    }

    @Test
//    ensures null is returned when hold panel is empty and user decides to swap
    void testSwapWithHeld_FirstSwap_ReturnsNull() {
        Brick brick = new IBrick();

        Brick result = queueManager.swapWithHeld(brick);

        assertNull(result);
        assertEquals(brick, queueManager.getHeldBrick());
    }

    @Test
//    ensures Brick object in hold panel is returned when user decides to swap
    void testSwapWithHeld_SecondSwap_ReturnsHeldBrick() {
        Brick first = new IBrick();
        Brick second = new IBrick();

        queueManager.swapWithHeld(first);
        Brick result = queueManager.swapWithHeld(second);

        assertEquals(first, result);
        assertEquals(second, queueManager.getHeldBrick());
    }

    @Test
//    method should return false when Brick object is swapped
    void testSwapWithHeld_SetsHoldUsed() {
        queueManager.swapWithHeld(new IBrick());

        assertFalse(queueManager.canUseHold());
    }

    @Test
//    method should return true again on a new turn
    void testResetHoldForNewTurn_AllowsHoldAgain() {
        queueManager.swapWithHeld(new IBrick());
        queueManager.resetHoldForNewTurn();

        assertTrue(queueManager.canUseHold());
    }

    @Test
//    ensures the hold panel is cleared on a new game
//    ensures the hold flag is true on a new game
    void testResetQueueForNewGame_ClearsHeldBrick() {
        queueManager.swapWithHeld(new IBrick());
        queueManager.resetQueueForNewGame();

        assertNull(queueManager.getHeldBrick());
        assertTrue(queueManager.canUseHold());
    }
}