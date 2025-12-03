package com.comp2042.bricks.production.blueprints;

import com.comp2042.logic.engine.SimpleBoard;
import com.comp2042.bricks.actions.BrickRotator;

import java.util.List;

/**
 * Interface to be implemented by all Brick-shape-classes
 */
public interface Brick {

    /**
     * List: can access elements by their integer index (position in the list), and search for elements in the list.
     * <p></p>
     * getShapeMatrix method is abstract to ensure it is implemented by all Brick-shape-classes.
     * This ensures that when an object of a Brick-shape-class is created, the methods in classes BrickRotater and SimpleBoard
     * can obtain all orientations of the shape of the Brick-shape-class (object) in the form of a matrix.
     * @see BrickRotator
     * @see SimpleBoard
     */
    List<int[][]> getShapeMatrix();
}
