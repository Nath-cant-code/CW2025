package com.comp2042.bricks.actions;

import com.comp2042.bricks.production.brick_shapes.IBrick;
import com.comp2042.logic.game_records.NextShapeInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Point;
import static org.junit.jupiter.api.Assertions.*;

class BrickRotatorTest {

    private BrickRotator rotator;
    private int[][] emptyMatrix;

    @BeforeEach
    void setUp() {
        rotator = new BrickRotator();
        rotator.setBrick(new IBrick());
        emptyMatrix = new int[25][10];
    }

    @Test
//    ensures the matrix of the Brick object is returned
    void testSetBrick_ResetsCurrentShape() {
        rotator.setBrick(new IBrick());

        assertNotNull(rotator.getCurrentShape());
    }

    @Test
//    ensures the List of orientations of the Brick is traversed properly
    void testNextClockRotation_CyclesThrough() {
        NextShapeInfo first = rotator.nextClockRotation();
        rotator.setCurrentShape(first.position());
        NextShapeInfo second = rotator.nextClockRotation();

        assertEquals(1, first.position());
        assertEquals(0, second.position());
    }

    @Test
//    ensures the List of orientations of the Brick is traversed in reverse properly
    void testNextAntiClockRotation_CyclesBackward() {
        NextShapeInfo first = rotator.nextAntiClockRotation();

        assertEquals(1, first.position());
    }

    @Test
//    method should return true if desired rotation is valid
    void testTryRotateBrick_ValidRotation_ReturnsTrue() {
        Point offset = new Point(4, 1);

        boolean result = rotator.tryRotateBrick(
                RotationDirection.CLOCKWISE,
                emptyMatrix,
                offset
        );

        assertTrue(result);
    }

    @Test
//    method should return false if desired rotation is valid
    void testTryRotateBrick_InvalidRotation_ReturnsFalse() {
        int[][] blockedMatrix = new int[25][10];
        for (int i = 0; i < 10; i++) {
            blockedMatrix[1][i] = 1;
            blockedMatrix[2][i] = 1;
            blockedMatrix[3][i] = 1;
        }
        Point offset = new Point(3, 0);

        boolean result = rotator.tryRotateBrick(
                RotationDirection.CLOCKWISE,
                blockedMatrix,
                offset
        );

        assertFalse(result);
    }
}