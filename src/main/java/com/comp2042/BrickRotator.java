package com.comp2042;

import com.comp2042.logic.bricks.Brick;

public class BrickRotator {

    private Brick brick;
    private int currentShape = 0;

    /**
     * Everytime this method is called, currentShape increments. <br>
     * This counter along with the modulus operator (%) helps to
     * cycle through the different orientations of a specific Brick-shape-object. <br>
     * nextShape will be used as an index to select the next orientation of the Brick-shape-object
     * <p>
     *     @return an object (with class NextShapeInfo) that contains: <br>
     *     - the matrix of the next orientation of the current Brick-shape-object <br>
     *     - and the index of said orientation in the (Brick-shape-object orientations) List, i.e. getShapeMatrix
     * </p>
     */
    public NextShapeInfo getNextShape() {
        int nextShape = currentShape;
        nextShape = (++nextShape) % brick.getShapeMatrix().size();
        return new NextShapeInfo(brick.getShapeMatrix().get(nextShape), nextShape);
    }

    /**
     * Whatever value the currentShape counter has, that will be the index of the getShapeMatrix List being accessed.
     * @return Orientation of current Brick-shape-object
     */
    public int[][] getCurrentShape() {
        return brick.getShapeMatrix().get(currentShape);
    }

    /**
     * Only used once in SimpleBoard.rotateLeftBrick() to set a new orientation for the current Brick-shape-object.
     * @param currentShape  New counter value to be used as the index (nextShape) to select the new orientation.
     */
    public void setCurrentShape(int currentShape) {
        this.currentShape = currentShape;
    }

    /**
     * No uses?
     * @param brick no uses
     */
    public void setBrick(Brick brick) {
        this.brick = brick;
        currentShape = 0;
    }


}
