package com.comp2042.logic.detection_system;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Point;
import static org.junit.jupiter.api.Assertions.*;

class SpecialShapeDetectorTest {

    private SpecialShapeDetector detector;

    @BeforeEach
    void setUp() {
        detector = new SpecialShapeDetector();
    }

    @Test
//    ensures that null is returned if no special shape exists in the playable area
    void testDetectSpecialShape_NoShape_ReturnsNull() {
        int[][] board = new int[25][10];

        Point result = detector.detectSpecialShape(board);

        assertNull(result);
    }

    @Test
//    ensures that the top left position of the special shape is returned
    void testDetectSpecialShape_WithShape_ReturnsPosition() {
        int[][] board = new int[25][10];
        board[2][0] = 1; board[2][1] = 1; board[2][2] = 1;
        board[3][0] = 1; board[3][2] = 1;
        board[4][0] = 1; board[4][1] = 1; board[4][2] = 1;

        Point result = detector.detectSpecialShape(board);

        assertNotNull(result);
        assertEquals(0, result.x);
        assertEquals(2, result.y);
    }

    @Test
//    method should return true when called
    void testMarkCompleted_SetsCompletedFlag() {
        detector.markCompleted();

        assertTrue(detector.isCompleted());
    }

    @Test
//    method should return null if the special shape has been formed once during the current game
//    even if the player forms the special shape again
    void testDetectSpecialShape_AfterCompleted_ReturnsNull() {
        int[][] board = new int[25][10];
        board[2][0] = 1; board[2][1] = 1; board[2][2] = 1;
        board[3][0] = 1; board[3][2] = 1;
        board[4][0] = 1; board[4][1] = 1; board[4][2] = 1;

        detector.markCompleted();
        Point result = detector.detectSpecialShape(board);

        assertNull(result);
    }

    @Test
//    ensures that the completion flag is reset when needed
    void testReset_ResetsCompletedFlag() {
        detector.markCompleted();
        detector.reset();

        assertFalse(detector.isCompleted());
    }
}