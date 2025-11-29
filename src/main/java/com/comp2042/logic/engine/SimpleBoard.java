package com.comp2042.logic.engine;

import java.awt.Point;

import com.comp2042.logic.records.ClearRow;
import com.comp2042.logic.records.ViewData;
import com.comp2042.logic.detection_system.CollisionDetector;
import com.comp2042.logic.detection_system.MatrixOperations;
import com.comp2042.logic.detection_system.SpecialShapeDetector;
import com.comp2042.bricks.actions.BrickActionCoordinator;
import com.comp2042.bricks.actions.BrickQueueManager;
import com.comp2042.bricks.actions.RotationDirection;
import com.comp2042.bricks.production.blueprints.Brick;
import com.comp2042.bricks.production.brick_generation_system.*;

import java.util.List;

import com.comp2042.renderer.runtime_refreshers.RefreshCoordinator;
import com.comp2042.ui.elements.LevelSystem;
import com.comp2042.ui.elements.Score;
import javafx.scene.shape.Rectangle;

/**
 * -----------------------------------REFACTORED-----------------------------------<br>
 * An object of this class is created in Main and only one object is created everytime the game (program) runs.<br>
 * Now focuses on coordinating game board operations. <br>
 * In essence, this is a wrapper class that encapsulates other sub-wrapper classes.<br>
 * This class no longer contains the logic for the functionalities, but calls delegated methods from other classes containing said logic.<br>
 * SOLID: Single Responsibility: Coordinates board state, delegates specific tasks. <br>
 * SOLID: Dependency Inversion: Implements Board, which other classes depend on, instead of depending on the details of this class.<br>
 * Design Pattern: Facade: Provides simple interface to complex subsystem
 */
public class SimpleBoard implements Board {
    private final int width;
    private final int height;
    private int[][] currentGameMatrix;
    private Point currentOffset;


    private final Score score;
    private final LevelSystem levelSystem;
    private final BrickQueueManager queueManager;
    private final BrickActionCoordinator brickActionCoordinator;
    public final SpecialShapeDetector specialShapeDetector;

    /**
     * Initialised in Main.java
     * @param width     Width of playable area.
     * @param height    Height of playable area.
     */
    public SimpleBoard(int width, int height) {
        this.width = width;
        this.height = height;
        currentGameMatrix = new int[width][height];
        this.levelSystem = new LevelSystem();
        this.specialShapeDetector = new SpecialShapeDetector();

        score = new Score();
        queueManager = new BrickQueueManager(new RandomBrickGenerator());
        brickActionCoordinator = new BrickActionCoordinator(queueManager, score);
    }

    /**
     * Passes displacement coordinates to mover method.
     * @return  TRUE if movement is valid, FALSE otherwise.
     */
    @Override
    public boolean moveBrickDown () {
        return  brickActionCoordinator.tryMove(
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
    public boolean moveBrickLeft () {
        return  brickActionCoordinator.tryMove(
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
    public boolean moveBrickRight () {
        return  brickActionCoordinator.tryMove(
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
    public boolean rotateBrickLeft () {
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
    public boolean rotateBrickRight () {
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
    public void snapBrick (RefreshCoordinator refreshCoordinator, Rectangle[][] displayMatrix) {
        currentOffset.y = brickActionCoordinator.executeSnap(currentGameMatrix, currentOffset);
    }

    /**
     * Passes createNewBrick() method in this class to holder method for it to run after hold is executed.
     * @return  ViewData object.
     */
    @Override
    public ViewData holdBrick () {
        Point newOffset = brickActionCoordinator.executeHold(this::createNewBrick);
        if (newOffset != null) {
            currentOffset = newOffset;
        }
        return getViewData();
    }

    /**
     * Get the Brick object in hold panel.
     * @return  Brick object in hold panel.
     */
    @Override
    public Brick getHeldBrick () {
        return queueManager.getHeldBrick();
    }

    /**
     * Get List of next 3 Brick objects in queue.
     * @return  List of next 3 Brick objects in queue.
     */
    @Override
    public List<ViewData> getNextBricksPreview() {
        return queueManager.getPreviewData();
    }

    /**
     * Get currentGameMatrix
     * @return  currentGameMatrix, the current game board with all the Brick objects in place.
     */
    @Override
    public int[][] getBoardMatrix() {
        return currentGameMatrix;
    }

    /**
     * Creates a ViewData object with fields:<br>
     * current Brick-shape-object's current orientation, B-s-o's current x-coordinates,
     * B-s-o's current y-coordinates, the first orientation in the brickMatrix of the first (top) Brick-shape-object in the Deque, nextBricks.
     * @return  ViewData object
     */
    @Override
    public ViewData getViewData() {
        return new ViewData (
                brickActionCoordinator.getCurrentShape(),
                (int) currentOffset.getX(),
                (int) currentOffset.getY(),
                queueManager.getNextBrick().getShapeMatrix().getFirst());
    }

    /**
     * Get game score.
     * @return  Game score.
     */
    @Override
    public Score getScore() {
        return score;
    }

    /**
     * Get LevelSystem object.
     * @return  LevelSystem object.
     */
    @Override
    public LevelSystem getLevelSystem() {
        return levelSystem;
    }

    /**
     * Calls merge() to assimilate the current Brick-shape-object into the game's playable area (currentGameMatrix).
     */
    @Override
    public void mergeBrickToBackground() {
        queueManager.resetHoldForNewTurn();
        currentGameMatrix = MatrixOperations.merge(
                currentGameMatrix,
                brickActionCoordinator.getCurrentShape(),
                (int) currentOffset.getX(),
                (int) currentOffset.getY()
        );
    }

    /**
     * Creates a ClearRow object that contains information on fields
     * such as linesRemoved, newMatrix (new version of currentGameMatrix), and scoreBonus.<br>
     * Sets the currentGameMatrix to the newMatrix (version of playable area matrix after rows have been cleared).
     * @return  The ClearRow object, so that the caller of this method can access the fields of the object.
     */
    @Override
    public ClearRow clearRows() {
        ClearRow clearRow = MatrixOperations.checkRemoving(currentGameMatrix);
        currentGameMatrix = clearRow.newMatrix();

        if (clearRow.linesRemoved() > 0) {
            levelSystem.addClearedRows(clearRow.linesRemoved());
        }

        return clearRow;
    }

    /**
     * Clears the entire board by setting the game matrix to the default parameters.
     */
    @Override
    public void clearEntireBoard() {
        currentGameMatrix = new int[width][height];
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

    /**
     * Clears entire board.<br>
     * Resets score, levels, and allow special shape bonus.<br>
     * Calls createNewBrick() to generate a new Brick from the top of the Deque, newBricks, at the spawn point.
     */
    @Override
    public void newGame() {
        clearEntireBoard();
        score.reset();
        levelSystem.reset();
        resetSpecialShape();
        createNewBrick();
    }

    /**
     * Calls method to scan the playable area for the special shape.
     * @return  Starting point of the special shape in the playable area.
     */
    @Override
    public Point checkSpecialShape() {
        return specialShapeDetector.detectSpecialShape(currentGameMatrix);
    }

    /**
     * Calls method to check if player has already formed the special shape in the current game.
     * @return  TRUE if formed before, FALSE otherwise.
     */
    @Override
    public boolean isSpecialShapeCompleted() {
        return specialShapeDetector.isCompleted();
    }

    /**
     * Allows the player to form the special shape again on a new game.
     */
    @Override
    public void resetSpecialShape() {
        specialShapeDetector.reset();
    }

    /**
     * Notify the indicator that the player has formed the special shape during the current game.
     */
    @Override
    public void markSpecialShapeCompleted() {
        specialShapeDetector.markCompleted();
    }
}
