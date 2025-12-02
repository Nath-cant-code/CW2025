package com.comp2042.bricks.actions;

import com.comp2042.logic.detection_system.CollisionDetector;
import com.comp2042.logic.game_records.NextShapeInfo;
import com.comp2042.bricks.production.blueprints.Brick;

import java.awt.*;

/**
 * This class manages the selection of the possible orientations of a Brick-shape-object.<br>
 * SOLID: Single Responsibility - Only manages rotation
 */
public class BrickRotator {

    private Brick brick;
    private int currentShape = 0;

    /**
     * Attempts to rotate the brick in the specified direction.
     * @param rd Rotation direction
     * @param gameMatrix Current game matrix
     * @param currentOffset Current brick position
     * @return true if rotation successful, false otherwise
     */
    public boolean tryRotateBrick(RotationDirection rd, int[][] gameMatrix, Point currentOffset) {
        NextShapeInfo nextShape = nextRotation(rd);

        boolean conflict = CollisionDetector.wouldCollide(
                gameMatrix,
                nextShape.shape(),
                (int) currentOffset.getX(),
                (int) currentOffset.getY()
        );

        if (!conflict) {
            setCurrentShape(nextShape.position());
            return true;
        }
        return false;
    }

    /**
     * Calls a method based on the RotationDirection received.
     * @param rd    RotationDirection from rotateBrick().
     * @return      Respective method call.
     */
    public NextShapeInfo nextRotation (RotationDirection rd) {
        return switch (rd) {
            case CLOCKWISE      -> nextClockRotation();
            case ANTI_CLOCKWISE -> nextAntiClockRotation();
        };
    }

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
    public NextShapeInfo nextClockRotation() {
        int nextShape = currentShape;
        nextShape = (++nextShape) % brick.getShapeMatrix().size();
        return new NextShapeInfo(brick.getShapeMatrix().get(nextShape), nextShape);
    }

    /**
     * This method is the opposite of nextClockRotation. <br>
     * ***IMPORTANT***<br>
     * All Brick-shape-classes have been adjusted to rotate in a clockwise manner in ascending index value.<br>
     * Hence, for anti-clockwise rotation, we need to loop to the last index and return back to the first index.<br>
     * <p>
     *     @return an object (with class NextShapeInfo) that contains: <br>
     *     - the matrix of the next orientation of the current Brick-shape-object <br>
     *     - and the index of said orientation in the (Brick-shape-object orientations) List, i.e. getShapeMatrix
     * </p>
     */
    public NextShapeInfo nextAntiClockRotation() {
        int nextShape = currentShape;
        nextShape = (nextShape == 0) ? (brick.getShapeMatrix().size() - 1) : (nextShape - 1);
        return new NextShapeInfo(brick.getShapeMatrix().get(nextShape), nextShape);
    }

    /**
     * Only used once in SimpleBoard.rotateBrickLeft() to set a NEW orientation for the current Brick-shape-object.<br>
     * @param currentShape  New counter value to be used as the index (nextShape) to select the new orientation.
     */
    public void setCurrentShape(int currentShape) {
        this.currentShape = currentShape;
    }

    /**
     * Whatever value the currentShape counter has, that will be the index of the getShapeMatrix List being accessed.<br>
     * @return Orientation of current Brick-shape-object
     */
    public int[][] getCurrentShape() {
        return brick.getShapeMatrix().get(currentShape);
    }

    /**
     * Used in createNewBrick() in SimpleBoard. Sets the Brick-shape-object to the first orientation in its brickMatrix.<br>
     * @param brick The first (top) Brick-shape-object in the Deque, nextBricks.
     */
    public void setBrick(Brick brick) {
        this.brick = brick;
        currentShape = 0;
    }

    /**
     * Gets Brick object.
     * @return  Brick object in class.
     */
    public Brick getBrick () {
        return this.brick;
    }
}
