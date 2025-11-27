package com.comp2042.board;

import com.comp2042.board.composite_bricks.DownData;
import com.comp2042.board.composite_bricks.ViewData;
import com.comp2042.brick_actions.RotationDirection;
import com.comp2042.bricks.Brick;
import com.comp2042.renderer.concrete_refreshers.RefreshCoordinator;
import com.comp2042.ui.LevelSystem;
import com.comp2042.ui.Score;
import javafx.scene.shape.Rectangle;

import java.util.List;

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

    Brick getHeldBrick ();

    List<ViewData> getNextBricksPreview();

    int[][] getBoardMatrix();

    ViewData getViewData();

    LevelSystem getLevelSystem();

    void mergeBrickToBackground();

    ClearRow clearRows();

    Score getScore();

    boolean createNewBrick();

    void newGame();
}
