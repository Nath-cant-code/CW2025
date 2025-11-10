package com.comp2042;

public final class ClearRow {

    private final int linesRemoved;
    private final int[][] newMatrix;
    private final int scoreBonus;

    /**
     * This constructor is used to create an object in checkRemoving() in MatrixOperations. <br>
     * @param linesRemoved  clearedRows.size().
     * @param newMatrix     tmp -> holder for the newly formed currentGameMatrix in checkRemoving().
     * @param scoreBonus    score for clearing a number of rows.
     */
    public ClearRow(int linesRemoved, int[][] newMatrix, int scoreBonus) {
        this.linesRemoved = linesRemoved;
        this.newMatrix = newMatrix;
        this.scoreBonus = scoreBonus;
    }

    public int getLinesRemoved() {
        return linesRemoved;
    }

    public int[][] getNewMatrix() {
        return MatrixOperations.copy(newMatrix);
    }

    public int getScoreBonus() {
        return scoreBonus;
    }
}
