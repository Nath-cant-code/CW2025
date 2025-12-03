package com.comp2042.bricks.production.brick_generation_system;

import com.comp2042.logic.engine.SimpleBoard;
import com.comp2042.bricks.production.blueprints.Brick;

import java.util.List;

/**
 * Interface to be implemented by RandomBrickGenerator and SimpleBoard.
 * @see RandomBrickGenerator
 * @see SimpleBoard
 */
public interface BrickGenerator {

    /**
     * Implemented by RandomBrickGenerator.
     * @see RandomBrickGenerator
     */
    Brick getBrick();

    /**
     * Implemented by RandomBrickGenerator, then used by SimpleBoard.
     * @see RandomBrickGenerator
     * @see SimpleBoard
     */
    Brick getNextBrick();

    List<Brick> getUpcomingBricks();

    /**
     * Resets the brick queue for a new game.
     * Implemented by RandomBrickGenerator.
     */
    void resetQueue();
}
