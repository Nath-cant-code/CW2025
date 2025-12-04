package com.comp2042.bricks.actions;

import org.junit.jupiter.api.Test;
import java.awt.Point;
import static org.junit.jupiter.api.Assertions.*;

class BrickMoverTest {

    @Test
//    method should return true if movement is valid and update position of Brick
    void testTryMoveBrick_ValidMove_UpdatesOffsetAndReturnsTrue() {
        int[][] matrix = new int[10][10];
        int[][] brick = {{1, 1}, {1, 1}};
        Point offset = new Point(4, 0);

        boolean result = BrickMover.tryMoveBrick(matrix, brick, offset, 0, 1);

        assertTrue(result);
        assertEquals(4, offset.x);
        assertEquals(1, offset.y);
    }

    @Test
//    method should return false if movement is invalid and retain current position of Brick
    void testTryMoveBrick_InvalidMove_DoesNotUpdateOffset() {
        int[][] matrix = new int[10][10];
        matrix[1][4] = 1;
        int[][] brick = {{1, 1}, {1, 1}};
        Point offset = new Point(4, 0);

        boolean result = BrickMover.tryMoveBrick(matrix, brick, offset, 0, 1);

        assertFalse(result);
        assertEquals(4, offset.x);
        assertEquals(0, offset.y);
    }

    @Test
//    same situation but with left movement
    void testTryMoveBrick_MoveLeft_Works() {
        int[][] matrix = new int[10][10];
        int[][] brick = {{1, 1}, {1, 1}};
        Point offset = new Point(4, 4);

        boolean result = BrickMover.tryMoveBrick(matrix, brick, offset, -1, 0);

        assertTrue(result);
        assertEquals(3, offset.x);
        assertEquals(4, offset.y);
    }

    @Test
//    same situation but with right movement
    void testTryMoveBrick_MoveRight_Works() {
        int[][] matrix = new int[10][10];
        int[][] brick = {{1, 1}, {1, 1}};
        Point offset = new Point(4, 4);

        boolean result = BrickMover.tryMoveBrick(matrix, brick, offset, 1, 0);

        assertTrue(result);
        assertEquals(5, offset.x);
        assertEquals(4, offset.y);
    }
}