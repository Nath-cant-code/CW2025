package com.comp2042.board;

import com.comp2042.board.composite_bricks.DownData;
import com.comp2042.board.composite_bricks.ViewData;
import com.comp2042.brick_actions.RotationDirection;
import com.comp2042.renderer.RefreshCoordinator;
import com.comp2042.ui.Score;
import javafx.scene.shape.Rectangle;

/**
 * This interface and its abstract methods are implemented by SimpleBoard.
 * <p></p>
 * GameController creates a SimpleBoard object and uses the methods in implemented by SimpleBoard.
 */
public interface Board {

    boolean moveBrickDown();

    boolean moveBrickLeft();

    boolean moveBrickRight();

    boolean rotateBrickLeft();

    boolean rotateBrickRight();

    boolean rotateBrick(RotationDirection rd);

    DownData snapBrick (RefreshCoordinator refreshCoordinator, Rectangle[][] displayMatrix);

    ViewData holdBrick ();

    boolean createNewBrick();

    int[][] getBoardMatrix();

    ViewData getViewData();

    void mergeBrickToBackground();

    ClearRow clearRows();

    Score getScore();

    void newGame();
}
