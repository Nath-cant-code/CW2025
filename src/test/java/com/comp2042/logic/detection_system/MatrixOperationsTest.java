package com.comp2042.logic.detection_system;

import com.comp2042.logic.game_records.ClearRow;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MatrixOperationsTest {

    @Test
//    method should create an identical copy of any given matrix
    void testCopy_CreatesIndependentCopy() {
        int[][] original = {{1, 2}, {3, 4}};
        int[][] copy = MatrixOperations.copy(original);

        copy[0][0] = 99;

        assertEquals(1, original[0][0]);
        assertEquals(99, copy[0][0]);
    }

    @Test
//    method should combine Brick object with playable area in the correct positions
    void testMerge_CombinesBrickAndMatrix() {
        int[][] matrix = new int[5][5];
        int[][] brick = {{1, 1}, {1, 1}};

        int[][] result = MatrixOperations.merge(matrix, brick, 2, 2);

        assertEquals(1, result[2][2]);
        assertEquals(1, result[2][3]);
        assertEquals(1, result[3][2]);
        assertEquals(1, result[3][3]);
        assertEquals(0, result[0][0]);
    }

    @Test
//    method should return zero for both lines removed and score if there are no complete rows
    void testCheckRemoving_NoFullRows_ReturnsZeroLinesRemoved() {
        int[][] matrix = new int[5][5];
        matrix[4][0] = 1;

        ClearRow result = MatrixOperations.checkRemoving(matrix);

        assertEquals(0, result.linesRemoved());
        assertEquals(0, result.scoreBonus());
    }

    @Test
//    given a case of having 1 row cleared, method should calculate correct corresponding attributes
    void testCheckRemoving_OneFullRow_RemovesAndReturnsScore() {
        int[][] matrix = new int[5][5];
        for (int j = 0; j < 5; j++) {
            matrix[4][j] = 1;
        }

        ClearRow result = MatrixOperations.checkRemoving(matrix);

        assertEquals(1, result.linesRemoved());
        assertEquals(100, result.scoreBonus());

        for (int j = 0; j < 5; j++) {
            assertEquals(0, result.newMatrix()[4][j]);
        }
    }

    @Test
//    given a case of having 2 rows cleared, method should calculate correct corresponding attributes
    void testCheckRemoving_TwoFullRows_RemovesAndCalculatesScore() {
        int[][] matrix = new int[5][5];
        for (int j = 0; j < 5; j++) {
            matrix[3][j] = 1;
            matrix[4][j] = 1;
        }

        ClearRow result = MatrixOperations.checkRemoving(matrix);

        assertEquals(2, result.linesRemoved());
        assertEquals(400, result.scoreBonus());
    }

    @Test
//    method should return false if there is unoccupied space
    void testIntersect_EmptySpaces_ReturnsFalse() {
        int[][] matrix = new int[10][10];
        int[][] brick = {{1, 1}, {1, 1}};

        assertFalse(MatrixOperations.intersect(matrix, brick, 0, 0));
    }

    @Test
//    method should return true if there space is occupied
    void testIntersect_OccupiedSpace_ReturnsTrue() {
        int[][] matrix = new int[10][10];
        matrix[1][1] = 1;
        int[][] brick = {{1, 1}, {1, 1}};

        assertTrue(MatrixOperations.intersect(matrix, brick, 0, 0));
    }
}