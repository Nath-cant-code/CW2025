package com.comp2042.logic.engine;

import com.comp2042.logic.records.ClearRow;
import com.comp2042.logic.records.ViewData;
import com.comp2042.bricks.production.blueprints.Brick;
import com.comp2042.renderer.runtime_refreshers.RefreshCoordinator;
import com.comp2042.ui.elements.LevelSystem;
import com.comp2042.ui.elements.Score;
import javafx.scene.shape.Rectangle;
import java.awt.Point;

import java.util.List;

/**
 * Implemented by super class SimpleBoard and subclass ActionBoard.
 */
public interface Board {

    boolean moveBrickDown();

    boolean moveBrickLeft();

    boolean moveBrickRight();

    boolean rotateBrickLeft();

    boolean rotateBrickRight();

    void snapBrick (RefreshCoordinator refreshCoordinator, Rectangle[][] displayMatrix);

    ViewData holdBrick ();

    Brick getHeldBrick ();

    List<ViewData> getNextBricksPreview();

    int[][] getBoardMatrix();

    ViewData getViewData();

    Score getScore();

    LevelSystem getLevelSystem();

    void mergeBrickToBackground();

    ClearRow clearRows();

    void clearEntireBoard();

    boolean createNewBrick();

    void newGame();

    Point checkSpecialShape();

    boolean isSpecialShapeCompleted();

    void resetSpecialShape();

    void markSpecialShapeCompleted();
}
