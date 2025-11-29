package com.comp2042.logic.records;

/**
 * A class that just contains a ClearRow object together with a ViewData object mainly for when a Brick object is moving down
 * because in said scenario, fields from both objects are often needed together.
 */
public record DownData(ClearRow clearRow, ViewData viewData) {
    /**
     * Creating a DownData object requires both objects.
     *
     * @param clearRow Object that contains information about the number of lines being cleared (linesRemoved),
     *                 new game state of playable area (newMatrix), and the points obtained by the player for clearing a number of rows (scoreBonus).
     * @param viewData Object that contains information on the current Brick-shape-object's
     *                 current orientation, coordinates, and the first orientation of the next Brick-shape-object in line.
     */
    public DownData {
    }

    /**
     * @return ClearRow object.
     */
    @Override
    public ClearRow clearRow() {
        return clearRow;
    }

    /**
     * @return ViewData object.
     */
    @Override
    public ViewData viewData() {
        return viewData;
    }
}
