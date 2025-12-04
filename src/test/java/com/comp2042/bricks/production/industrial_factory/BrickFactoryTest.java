package com.comp2042.bricks.production.industrial_factory;

import com.comp2042.bricks.production.blueprints.Brick;
import com.comp2042.bricks.production.brick_shapes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BrickFactoryTest {

    private BrickFactory factory;

    @BeforeEach
    void setUp() {
        factory = new BrickFactory();
    }

    @Test
//    ensures I Brick is created when prompted
    void testCreateBrick_IBrick() {
        Brick brick = factory.createBrick(BrickType.I);
        assertInstanceOf(IBrick.class, brick);
    }

    @Test
//    ensures J Brick is created when prompted
    void testCreateBrick_JBrick() {
        Brick brick = factory.createBrick(BrickType.J);
        assertInstanceOf(JBrick.class, brick);
    }

    @Test
//    ensures L Brick is created when prompted
    void testCreateBrick_LBrick() {
        Brick brick = factory.createBrick(BrickType.L);
        assertInstanceOf(LBrick.class, brick);
    }

    @Test
//    ensures O Brick is created when prompted
    void testCreateBrick_OBrick() {
        Brick brick = factory.createBrick(BrickType.O);
        assertInstanceOf(OBrick.class, brick);
    }

    @Test
//    ensures S Brick is created when prompted
    void testCreateBrick_SBrick() {
        Brick brick = factory.createBrick(BrickType.S);
        assertInstanceOf(SBrick.class, brick);
    }

    @Test
//    ensures T Brick is created when prompted
    void testCreateBrick_TBrick() {
        Brick brick = factory.createBrick(BrickType.T);
        assertInstanceOf(TBrick.class, brick);
    }

    @Test
//    ensures Z Brick is created when prompted
    void testCreateBrick_ZBrick() {
        Brick brick = factory.createBrick(BrickType.Z);
        assertInstanceOf(ZBrick.class, brick);
    }
}