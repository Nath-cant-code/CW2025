package com.comp2042.logic.detection_system;

/**
 * Handles collision detection logic.<br>
 * SOLID: Single Responsibility - Only detects collisions<br>
 * Design Pattern: Strategy - Collision rules can be changed independently<br>
 */
public class CollisionDetector {

    /**
     * Checks if Brick object will collide with wall or fallen blocks.
     * @param matrix    Game matrix
     * @param brick     Brick matrix
     * @param x         Brick object's x-coordinates
     * @param y         Brick object's x-coordinates
     * @return          TRUE if there is collision, i.e. movement is invalid, FALSE otherwise
     */
    public static boolean wouldCollide(int[][] matrix, int[][] brick, int x, int y) {
        return MatrixOperations.intersect(matrix, brick, x, y);
    }

    /**
     * Simplifies inversed call of wouldCollide.
     * @param matrix    Game matrix
     * @param brick     Brick matrix
     * @param x         Brick object's x-coordinates
     * @param y         Brick object's x-coordinates
     * @return          TRUE if there is collision, i.e. movement is invalid, FALSE otherwise
     */
    public static boolean isValidSpawnPosition(int[][] matrix, int[][] brick, int x, int y) {
        return !wouldCollide(matrix, brick, x, y);
    }
}