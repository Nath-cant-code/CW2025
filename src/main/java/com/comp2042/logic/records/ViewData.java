package com.comp2042.logic.records;

import com.comp2042.logic.detection_system.MatrixOperations;

/**
 * This class is to create an object that contains information on the current Brick-shape-object's
 * current orientation, coordinates, and the first orientation of the next Brick-shape-object in line.
 */
public record ViewData(int[][] brickData, int xPosition, int yPosition, int[][] nextBrickData) {

    /**
     * @param brickData     Current Brick-shape-object's current orientation.
     * @param xPosition     Brick-shape-object's current x-coordinates.
     * @param yPosition     Brick-shape-object's current y-coordinates.
     * @param nextBrickData The first orientation in the brickMatrix of the first (top) Brick-shape-object in the Deque, nextBricks.
     */
    public ViewData {
    }

    /**
     * @return Copy of the current orientation of the Brick-shape-object that was passed.
     */
    @Override
    public int[][] brickData() {
        return MatrixOperations.copy(brickData);
    }

    /**
     * @return x-coordinates of the Brick-shape-object that was passed.
     */
    @Override
    public int xPosition() {
        return xPosition;
    }

    /**
     * @return y-coordinates of the Brick-shape-object that was passed.
     */
    @Override
    public int yPosition() {
        return yPosition;
    }

    /**
     * @return Copy of the first orientation in the brickMatrix of the first (top) Brick-shape-object in the Deque, nextBricks.
     */
    @Override
    public int[][] nextBrickData() {
        return MatrixOperations.copy(nextBrickData);
    }
}
