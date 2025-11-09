package com.comp2042.logic.bricks;

/**
 * Interface to be implemented by RandomBrickGenerator and SimpleBoard.
 * @see RandomBrickGenerator
 * @see com.comp2042.SimpleBoard
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
     * @see com.comp2042.SimpleBoard
     */
    Brick getNextBrick();
}
