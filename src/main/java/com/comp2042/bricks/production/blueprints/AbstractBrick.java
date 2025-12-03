package com.comp2042.bricks.production.blueprints;

import com.comp2042.logic.detection_system.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class to extract the getShapeMatrix method from all the brick_shape_classes.
 */
public abstract class AbstractBrick implements Brick{
    protected final List<int[][]> brickMatrix = new ArrayList<>();

    /**
     * @return A deep copy of a Brick class so that when rotating the object created,
     * the shape of the IBrick in this class is not altered. Deep copy prevents changes to the
     * original when the copy is altered.
     */
    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }
}
