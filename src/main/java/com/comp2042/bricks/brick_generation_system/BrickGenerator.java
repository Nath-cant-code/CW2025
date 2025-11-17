package com.comp2042.bricks.brick_generation_system;

import com.comp2042.board.SimpleBoard;
import com.comp2042.bricks.Brick;

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
}
