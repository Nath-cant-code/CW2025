package com.comp2042.bricks.production.industrial_factory;

import com.comp2042.bricks.production.blueprints.Brick;
import com.comp2042.bricks.production.brick_shapes.*;

/**
 * Creates instances for all Brick classes
 */
public class BrickFactory {

    /**
     * Creates Brick object selected.
     * @param type Brick class letter
     * @return  Brick object
     */
    public Brick createBrick (BrickType type) {
        return switch (type) {
            case I -> new IBrick();
            case J -> new JBrick();
            case L -> new LBrick();
            case O -> new OBrick();
            case S -> new SBrick();
            case T -> new TBrick();
            case Z -> new ZBrick();
        };
    }
}
