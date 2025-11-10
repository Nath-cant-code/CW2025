package com.comp2042;

/**
 * This class is to create an object that contains information on the current Brick-shape-object's
 * current orientation, coordinates, and the first orientation of the next Brick-shape-object in line.
 */
public final class ViewData {

    private final int[][] brickData;
    private final int xPosition;
    private final int yPosition;
    private final int[][] nextBrickData;

    /**
     * @param brickData     Current Brick-shape-object's current orientation.
     * @param xPosition     Brick-shape-object's current x-coordinates.
     * @param yPosition     Brick-shape-object's current y-coordinates.
     * @param nextBrickData The first orientation in the brickMatrix of the first (top) Brick-shape-object in the Deque, nextBricks.
     */
    public ViewData(int[][] brickData, int xPosition, int yPosition, int[][] nextBrickData) {
        this.brickData = brickData;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.nextBrickData = nextBrickData;
    }

    /**
     * @return  Copy of the current orientation of the Brick-shape-object that was passed.
     */
    public int[][] getBrickData() {
        return MatrixOperations.copy(brickData);
    }

    /**
     * @return x-coordinates of the Brick-shape-object that was passed.
     */
    public int getxPosition() {
        return xPosition;
    }

    /**
     * @return  y-coordinates of the Brick-shape-object that was passed.
     */
    public int getyPosition() {
        return yPosition;
    }

    /**
     * @return  Copy of the first orientation in the brickMatrix of the first (top) Brick-shape-object in the Deque, nextBricks.
     */
    public int[][] getNextBrickData() {
        return MatrixOperations.copy(nextBrickData);
    }
}
