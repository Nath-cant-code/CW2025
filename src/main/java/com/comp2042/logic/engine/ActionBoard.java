package com.comp2042.logic.engine;

import com.comp2042.bricks.actions.RotationDirection;
import com.comp2042.bricks.production.blueprints.Brick;
import com.comp2042.logic.detection_system.CollisionDetector;
import com.comp2042.logic.game_records.ViewData;
import com.comp2042.renderer.runtime_refreshers.RefreshCoordinator;
import javafx.scene.shape.Rectangle;
import java.awt.Point;

/**
 * Extends SimpleBoard to handle all brick action operations.<br>
 * SOLID: Single Responsibility - Only manages brick actions<br>
 * Design Pattern: Strategy - Encapsulates Brick action behaviours<br>
 */
public class ActionBoard extends SimpleBoard {

    /**
     * Passes parameters to super class.
     * @param width     Playable area width.
     * @param height    Playable area height.
     */
    public ActionBoard(int width, int height) {
        super(width, height);
    }

    /**
     * Passes displacement coordinates to mover method.
     * @return  TRUE if movement is valid, FALSE otherwise.
     */
    @Override
    public boolean moveBrickDown() {
        return brickActionCoordinator.tryMove(
                currentGameMatrix,
                currentOffset,
                0, 1
        );
    }

    /**
     * Passes displacement coordinates to mover method.
     * @return  TRUE if movement is valid, FALSE otherwise.
     */
    @Override
    public boolean moveBrickLeft() {
        return brickActionCoordinator.tryMove(
                currentGameMatrix,
                currentOffset,
                -1, 0
        );
    }

    /**
     * Passes displacement coordinates to mover method.
     * @return  TRUE if movement is valid, FALSE otherwise.
     */
    @Override
    public boolean moveBrickRight() {
        return brickActionCoordinator.tryMove(
                currentGameMatrix,
                currentOffset,
                1, 0
        );
    }

    /**
     * Passes corresponding RotationDirection to rotater method.
     * @return TRUE if VALID rotation, FALSE if INVALID rotation.
     */
    @Override
    public boolean rotateBrickLeft() {
        return brickActionCoordinator.tryRotate(
                RotationDirection.ANTI_CLOCKWISE,
                currentGameMatrix,
                currentOffset
        );
    }

    /**
     * ------------------------------------ADDED A ROTATE RIGHT METHOD------------------------------------<br>
     * Passes corresponding RotationDirection to rotater method.
     * @return TRUE if VALID rotation, FALSE if INVALID rotation.
     */
    @Override
    public boolean rotateBrickRight() {
        return brickActionCoordinator.tryRotate(
                RotationDirection.CLOCKWISE,
                currentGameMatrix,
                currentOffset
        );
    }

    /**
     * Calls snap brick method.
     * @param refreshCoordinator    Object to render the Brick object after snap (hard drop)
     * @param displayMatrix         Current game matrix
     */
    @Override
    public void snapBrick(RefreshCoordinator refreshCoordinator, Rectangle[][] displayMatrix) {
        currentOffset.y = brickActionCoordinator.executeSnap(currentGameMatrix, currentOffset);
    }

    /**
     * Passes createNewBrick() method in this class to holder method for it to run after hold is executed.
     * @return  ViewData object.
     */
    @Override
    public ViewData holdBrick() {
        Point newOffset = brickActionCoordinator.executeHold(this::createNewBrick);
        if (newOffset != null) {
            currentOffset = newOffset;
        }
        return getViewData();
    }

    /**
     * ------------------------------------SPAWN POINT CHANGED HERE------------------------------------<br>
     * Creates a new Brick object (currentBrick) by popping the first (top) Brick-shape-object from the Deque, nextBricks. <br>
     * Calls setBrick() to set the new Brick-shape-object's orientation to the first in its brickMatrix List. <br>
     * currentOffset is the coordinates in the playable area (currentGameMatrix) where the new Brick-shape-object will be generated,
     * AKA the spawn point. <br>
     * @return  If the spawn point is VALID, createNewBrick() returns FALSE,
     * if spawn point in INVALID, createNewBrick() returns TRUE.
     */
    @Override
    public boolean createNewBrick() {
        Brick currentBrick = queueManager.generateBrick();
        brickActionCoordinator.setBrick(currentBrick);
        currentOffset = new Point(4, 1);
        return !CollisionDetector.isValidSpawnPosition(
                currentGameMatrix,
                brickActionCoordinator.getCurrentShape(),
                (int) currentOffset.getX(),
                (int) currentOffset.getY()
        );
    }
}