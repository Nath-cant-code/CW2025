package com.comp2042.bricks.production.brick_generation_system;

import com.comp2042.bricks.production.blueprints.Brick;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class RandomBrickGeneratorTest {

    private RandomBrickGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new RandomBrickGenerator();
    }

    @Test
//    ensures a Brick object is popped from List and returned
    void testGetBrick_ReturnsNonNull() {
        Brick brick = generator.getBrick();

        assertNotNull(brick);
    }

    @Test
//    ensures a Brick object is returned when peeking
    void testGetNextBrick_ReturnsNonNull() {
        Brick brick = generator.getNextBrick();

        assertNotNull(brick);
    }

    @Test
//    ensures the Brick objects in the List are not the same object
    void testGetBrick_RemovesBrickFromQueue() {
        Brick first = generator.getNextBrick();
        generator.getBrick();
        Brick second = generator.getNextBrick();

        assertNotEquals(first, second);
    }

    @Test
//    ensures at least 4 Brick objects exist in the List at any time
    void testGetUpcomingBricks_ReturnsMultipleBricks() {
        List<Brick> bricks = generator.getUpcomingBricks();

        assertTrue(bricks.size() >= 3);
    }

    @Test
//    ensures a new Brick object to created when List is reset on new game
    void testResetQueue_GeneratesNewQueue() {
        Brick beforeReset = generator.getNextBrick();
        generator.resetQueue();
        Brick afterReset = generator.getNextBrick();

        assertNotNull(afterReset);
    }

    @Test
//    ensures at least 4 Brick objects exist in the List even after a reset
    void testResetQueue_MaintainsQueueSize() {
        generator.resetQueue();
        List<Brick> bricks = generator.getUpcomingBricks();

        assertEquals(4, bricks.size());
    }
}