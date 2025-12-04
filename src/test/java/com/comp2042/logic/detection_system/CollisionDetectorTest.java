package com.comp2042.logic.detection_system;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CollisionDetectorTest {

    @Test
//    method should return false if there is empty space
    void testWouldCollide_WithEmptyMatrix_ReturnsFalse() {
        int[][] matrix = new int[10][10];
        int[][] brick = {{1, 1}, {1, 1}};

        assertFalse(CollisionDetector.wouldCollide(matrix, brick, 0, 0));
    }

    @Test
//    method should return true if there is a block occupying the space
    void testWouldCollide_WithOccupiedSpace_ReturnsTrue() {
        int[][] matrix = new int[10][10];
        matrix[1][1] = 1;
        int[][] brick = {{1, 1}, {1, 1}};

        assertTrue(CollisionDetector.wouldCollide(matrix, brick, 0, 0));
    }

    @Test
//    method should return true if Brick position will be out of bounds
    void testWouldCollide_OutOfBounds_ReturnsTrue() {
        int[][] matrix = new int[10][10];
        int[][] brick = {{1, 1}, {1, 1}};

        assertTrue(CollisionDetector.wouldCollide(matrix, brick, -1, 0));
        assertTrue(CollisionDetector.wouldCollide(matrix, brick, 0, 9));
        assertTrue(CollisionDetector.wouldCollide(matrix, brick, 9, 0));
    }

    @Test
//    method should return true if the spawn point is not occupied
    void testIsValidSpawnPosition_ValidPosition_ReturnsTrue() {
        int[][] matrix = new int[10][10];
        int[][] brick = {{1, 1}, {1, 1}};

        assertTrue(CollisionDetector.isValidSpawnPosition(matrix, brick, 4, 0));
    }

    @Test
//    method should return false if the spawn point is occupied
    void testIsValidSpawnPosition_InvalidPosition_ReturnsFalse() {
        int[][] matrix = new int[10][10];
        matrix[0][4] = 1;
        int[][] brick = {{1, 1}, {1, 1}};

        assertFalse(CollisionDetector.isValidSpawnPosition(matrix, brick, 3, 0));
    }
}