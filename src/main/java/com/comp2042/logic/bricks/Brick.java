package com.comp2042.logic.bricks;

import java.util.List;

/**
 * Interface to be implemented by all Brick-shape-classes
 */
public interface Brick {

    /**
     * List: can access elements by their integer index (position in the list), and search for elements in the list.
     * <p></p>
     * getShapeMatrix method is abstract to ensure it is implemented by all Brick-shape-classes
     * to further ensure that when an object of a Brick-shape-class is created, the methods in classes BrickRotater and SimpleBoard
     * can obtain all orientations of the shape of the Brick-shape-class (object) in the form of a matrix.
     * @see com.comp2042.BrickRotator
     * @see com.comp2042.SimpleBoard
     */
    List<int[][]> getShapeMatrix();
}
