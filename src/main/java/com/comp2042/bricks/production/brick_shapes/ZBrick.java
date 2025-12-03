package com.comp2042.bricks.production.brick_shapes;

import com.comp2042.bricks.production.blueprints.AbstractBrick;

/**
 * This class is final to ensure the shape of the brick never changes.
 */
public final class ZBrick extends AbstractBrick {
    /**
     * The constructor defines the shape of the brick in all possible orientations
     * and stores all the orientations in the List matrix, brickMatrix.
     * <p></p>
     * An object of the class will be randomly generated.
     * @see com.comp2042.bricks.production.brick_generation_system.RandomBrickGenerator
     */
    public ZBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {7, 7, 0, 0},
                {0, 7, 7, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 7, 0, 0},
                {7, 7, 0, 0},
                {7, 0, 0, 0},
                {0, 0, 0, 0}
        });
    }
}
