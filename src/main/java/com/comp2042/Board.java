package com.comp2042;

/**
 * This interface and its abstract methods are implemented by SimpleBoard.
 * <p></p>
 * GameController creates a SimpleBoard object and uses the methods in implemented by SimpleBoard.
 */
public interface Board {

    boolean moveBrickDown();

    boolean moveBrickLeft();

    boolean moveBrickRight();

    boolean rotateLeftBrick();

    boolean createNewBrick();

    int[][] getBoardMatrix();

    ViewData getViewData();

    void mergeBrickToBackground();

    ClearRow clearRows();

    Score getScore();

    void newGame();
}
