package com.comp2042;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class MatrixOperations {


    /**
     * We don't want to instantiate this utility class. <br>
     * Hence, this constructor is empty.
     */
    private MatrixOperations(){

    }

    /**
     * This is the method to be called to check (or predict) if the next (desired) orientation or position
     * of the current Brick-shape-object will exceed the playable area (currentMatrix or currentGameMatrix in SimpleBoard)
     * or if there is available space for the Brick-shape-object to fall into (i.e. if the space is unoccupied by another Brick-shape-object).<br>
     *
     * It does this by using a nested for loop to increment or decrement the x- and y- coordinates
     * of each block in the Brick-shape-object, and calls the checkOutOfBound method
     * to check if the new (desired) position or orientation is still in the playable area or not. <br>
     *
     * The matrix[targetY][targetX] != 0 checks if the space is available and unoccupied by another Brick-shape-object.
     *
     * @param matrix The playable area the bricks can fall in (currentMatrix or currentGameMatrix in SimpleBoard).
     * @param brick Current orientation of current Brick-shape-object.
     * @param x     dx relative to object being referenced.
     * @param y     dy relative to object being referenced.
     * @return  True if new (desired) orientation or position of current Brick-shape-object is invalid,
     * False if it is still within the playable area and the space is unoccupied.
     */
    public static boolean intersect(final int[][] matrix, final int[][] brick, int x, int y) {
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetX = x + i;
                int targetY = y + j;
                if (brick[j][i] != 0 && (checkOutOfBound(matrix, targetX, targetY) || matrix[targetY][targetX] != 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param matrix The playable area the bricks can fall in (currentMatrix or currentGameMatrix in SimpleBoard).
     * @param targetX   Incremented value of dx relative to object being referenced.
     * @param targetY   Incremented value of dy relative to object being referenced.
     * @return True if rotated orientation of Brick-shape-object is out of bounds,
     * False if orientation remains inside playable area.
     */
    private static boolean checkOutOfBound(int[][] matrix, int targetX, int targetY) {
        boolean returnValue = true;
        if (targetX >= 0 && targetY < matrix.length && targetX < matrix[targetY].length) {
            returnValue = false;
        }
        return returnValue;
    }

    /**
     * So far the only use is in merge() method. <br>
     * Intention of this method is to create a deep copy of the currentGameMatrix as to not affect the original matrix
     * when merging the matrix of the Brick-shape-object with the matrix of the currentGameMatrix. <br>
     * @param original Original and current matrix of the Brick-shape-object.
     * @return Copied version of the original and current matrix of the Brick-shape-object.
     */
    public static int[][] copy(int[][] original) {
        int[][] myInt = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            int[] aMatrix = original[i];
            int aLength = aMatrix.length;
            myInt[i] = new int[aLength];
            System.arraycopy(aMatrix, 0, myInt[i], 0, aLength);
        }
        return myInt;
    }

    /**
     * Assimilates the Brick-shape-object's matrix into the playable area's matrix (currentMatrix/currentGameMatrix). <br>
     * @param filledFields  currentGameMatrix.
     * @param brick         current (matrix) orientation of the Brick-shape-object.
     * @param x             dx relative to object being referenced.
     * @param y             dy relative to object being referenced.
     * @return              new version of currentGameMatrix with the newly added Brick-shape-object.
     */
    public static int[][] merge(int[][] filledFields, int[][] brick, int x, int y) {
        int[][] copy = copy(filledFields);
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetX = x + i;
                int targetY = y + j;
                if (brick[j][i] != 0) {
                    copy[targetY][targetX] = brick[j][i];
                }
            }
        }
        return copy;
    }

    /**
     * Checks for rows that have no unoccupied block spaces before clearing said rows
     * and shifting every row above the topmost deleted row down until it reaches a row that is above
     * a lower row that has at least 1 block occupying a space. <br>
     * @param matrix    currentGameMatrix
     * @return A ClearRow object with the fields (int linesRemoved, int[][] newMatrix, scoreBonus).
     */
    public static ClearRow checkRemoving(final int[][] matrix) {
        int[][] tmp = new int[matrix.length][matrix[0].length];
        Deque<int[]> newRows = new ArrayDeque<>();
        List<Integer> clearedRows = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            int[] tmpRow = new int[matrix[i].length];
            boolean rowToClear = true;
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    rowToClear = false;
                }
                tmpRow[j] = matrix[i][j];
            }
            if (rowToClear) {
                clearedRows.add(i);
            } else {
                newRows.add(tmpRow);
            }
        }
        for (int i = matrix.length - 1; i >= 0; i--) {
            int[] row = newRows.pollLast();
            if (row != null) {
                tmp[i] = row;
            } else {
                break;
            }
        }
        int scoreBonus = 50 * clearedRows.size() * clearedRows.size();
        return new ClearRow(clearedRows.size(), tmp, scoreBonus);
    }

    /**
     * Used in Brick-shape-classes only.
     * @param list A list of matrices, which is actually the list of
     *             all the different possible matrix orientations of a Brick-shape-object.
     * @return  A copy of the List of possible matrix orientations of the Brick-shape-object.
     */
    public static List<int[][]> deepCopyList(List<int[][]> list){
        return list.stream().map(MatrixOperations::copy).collect(Collectors.toList());
    }

}
