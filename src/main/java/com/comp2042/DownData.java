package com.comp2042;

/**
 * A class that just contains a ClearRow object together with a ViewData object mainly for when a Brick object is moving down
 * because in said scenario, fields from both objects are often needed together.
 */
public final class DownData {
    private final ClearRow clearRow;
    private final ViewData viewData;

    /**
     * Creating a DownData object requires both objects.
     * @param clearRow  Object that contains information about the number of lines being cleared (linesRemoved),
     *                  new game state of playable area (newMatrix), and the points obtained by the player for clearing a number of rows (scoreBonus).
     * @param viewData  Object that contains information on the current Brick-shape-object's
     *                  current orientation, coordinates, and the first orientation of the next Brick-shape-object in line.
     */
    public DownData(ClearRow clearRow, ViewData viewData) {
        this.clearRow = clearRow;
        this.viewData = viewData;
    }

    /**
     * @return  ClearRow object.
     */
    public ClearRow getClearRow() {
        return clearRow;
    }

    /**
     * @return  ViewData object.
     */
    public ViewData getViewData() {
        return viewData;
    }
}
