package com.comp2042.board;

/**
 * Handles collision detection logic.
 * SOLID: Single Responsibility - Only detects collisions
 * Design Pattern: Strategy - Collision rules can be changed independently
 */
public class CollisionDetector {

    public static boolean wouldCollide(int[][] matrix, int[][] brick, int x, int y) {
        return MatrixOperations.intersect(matrix, brick, x, y);
    }

    public static boolean isValidSpawnPosition(int[][] matrix, int[][] brick, int x, int y) {
        return !wouldCollide(matrix, brick, x, y);
    }
}