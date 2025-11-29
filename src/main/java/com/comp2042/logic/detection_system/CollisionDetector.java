package com.comp2042.logic.detection_system;

/**
 * Handles collision detection logic.<br>
 * SOLID: Single Responsibility - Only detects collisions<br>
 * Design Pattern: Strategy - Collision rules can be changed independently<br>
 */
public class CollisionDetector {

    public static boolean wouldCollide(int[][] matrix, int[][] brick, int x, int y) {
        return MatrixOperations.intersect(matrix, brick, x, y);
    }

    public static boolean isValidSpawnPosition(int[][] matrix, int[][] brick, int x, int y) {
        return !wouldCollide(matrix, brick, x, y);
    }
}