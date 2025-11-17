package com.comp2042.board.composite_bricks;

import com.comp2042.board.MatrixOperations;

/**
 * This class holds fields with information on shape (the matrix of the next orientation of the
 * Brick-shape-object being referenced) and position (the index of said matrix in the
 * Brick-shape-object's orientation List, brickMatrix).
 */
public record NextShapeInfo(int[][] shape, int position) {

    /**
     * Ensures that when an object of this class is created,
     * the next orientation and index of said orientation's matrix is passed.
     *
     * @param shape    Brick-shape-object's next orientation's matrix.
     * @param position Index of said matrix of orientation.
     */
    public NextShapeInfo {
    }

    /**
     * @return A copy of the Brick-shape-object's next orientation's matrix.
     */
    @Override
    public int[][] shape() {
        return MatrixOperations.copy(shape);
    }

    /**
     * @return The index of the Brick-shape-object's next orientation's matrix in List, brickMatrix.
     */
    @Override
    public int position() {
        return position;
    }
}
