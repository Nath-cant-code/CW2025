package com.comp2042.board;

/**
 * This class is important for when the player completely fills a row with blocks from Brick-shape-objects
 * and the whole row gets cleared, earning the player points. <br>
 * This class contains information about the number of lines being cleared (linesRemoved),
 * new game state of playable area (newMatrix), and the points obtained by the player for clearing a number of rows (scoreBonus).
 */
public record ClearRow(int linesRemoved, int[][] newMatrix, int scoreBonus) {

    /**
     * This constructor is used to create an object in checkRemoving() in MatrixOperations. <br>
     *
     * @param linesRemoved clearedRows.size().
     * @param newMatrix    tmp -> holder for the newly formed currentGameMatrix in checkRemoving().
     * @param scoreBonus   score for clearing a number of rows.
     */
    public ClearRow {
    }

    /**
     * @return clearedRows.size().
     */
    @Override
    public int linesRemoved() {
        return linesRemoved;
    }

    /**
     * @return tmp -> holder for the newly formed currentGameMatrix in checkRemoving().
     */
    @Override
    public int[][] newMatrix() {
        return MatrixOperations.copy(newMatrix);
    }

    /**
     * @return score for clearing a number of rows.
     */
    @Override
    public int scoreBonus() {
        return scoreBonus;
    }
}
