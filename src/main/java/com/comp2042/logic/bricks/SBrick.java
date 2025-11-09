package com.comp2042.logic.bricks;

import com.comp2042.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is final to ensure the shape of the brick never changes.
 */
final class SBrick implements Brick {

    private final List<int[][]> brickMatrix = new ArrayList<>();

    /**
     * The constructor defines the shape of the brick in all possible orientations
     * and stores all the orientations in the List matrix, brickMatrix.
     * <p></p>
     * An object of the class will be randomly generated.
     * @see RandomBrickGenerator
     */
    public SBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 5, 5, 0},
                {5, 5, 0, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {5, 0, 0, 0},
                {5, 5, 0, 0},
                {0, 5, 0, 0},
                {0, 0, 0, 0}
        });
    }

    /**
     * @return A deep copy of the SBrick class so that when rotating the object created,
     * the shape of the SBrick in this class is not altered. Deep copy prevents changes to the
     * original when the copy is altered.
     */
    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }
}
