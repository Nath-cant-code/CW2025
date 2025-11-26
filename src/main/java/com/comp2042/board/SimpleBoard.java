package com.comp2042.board;

import com.comp2042.board.composite_bricks.DownData;
import com.comp2042.board.composite_bricks.NextShapeInfo;
import com.comp2042.board.composite_bricks.ViewData;
import com.comp2042.brick_actions.BrickRotator;
import com.comp2042.brick_actions.RotationDirection;
import com.comp2042.bricks.Brick;
import com.comp2042.bricks.brick_generation_system.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import com.comp2042.renderer.concrete_refreshers.RefreshCoordinator;
import com.comp2042.ui.Score;
import javafx.scene.shape.Rectangle;

/**
 * This class implements Board and creates useful methods. <br>
 * An object of this class is created in GameController
 * and only one object is created everytime the game (program) runs.<br>
 * SimpleBoard object consists of the playable area where bricks will fall,
 * along with all methods that correspond to the player's actions.
 */
public class SimpleBoard implements Board {

    /**
     * It helps to think that brickRotater holds the Brick-shape-object that has ALREADY BEEN POPPED
     * from the Deque, nextBricks and is currently falling in the playable area (currentGameMatrix). <br>
     * While brickGenerator holds the first or top Brick-shape-object in the Deque, nextBricks,
     * that HAS NOT BEEN POPPED YET.
     */
    private final int width;
    private final int height;
    private final BrickGenerator brickGenerator;
    private final BrickRotator brickRotator;
    private int[][] currentGameMatrix;
    private Point currentOffset;
    private final Score score;
    private Brick heldBrick = null;
    private boolean holdUsedThisTurn = false;

    /**
     * This constructor makes it so that when a SimpleBoard object is created in GameController,
     * the size of the playable area must be specified. <br>
     * currentGameMatrix would be the playable area for the bricks to fall in. <br>
     * <p>
     *     brickGenerator: an object to either: <br>
     *     - getNextBrick(): look at the first (top) Brick-shape-object in the Deque (nextBricks) <br>
     *     - getBrick(): or pop the first (top) Brick-shape-object in the Deque, essentially deleting it from the Deque
     * </p>
     * @param width     the values represent how many Brick sub-blocks (or pixels) can fit across the playable area.
     * @param height    the values represent how many Brick sub-blocks (or pixels)
     *                  can stack on top of each other in the playable area up until the Brick generation area (spawn point).
     */
    public SimpleBoard(int width, int height) {
        this.width = width;
        this.height = height;
        currentGameMatrix = new int[width][height];
        brickGenerator = new RandomBrickGenerator();
        brickRotator = new BrickRotator();
        score = new Score();
    }

    public List<ViewData> getNextBricksPreview() {
        List<ViewData> previews = new ArrayList<>();

        // Get the actual queue from the brick generator
        List<Brick> actualQueue = brickGenerator.getUpcomingBricks();

        for (int i = 0; i < Math.min(3, actualQueue.size()); i++) {
            Brick brick = actualQueue.get(i);
            int[][] shape = brick.getShapeMatrix().getFirst();

            ViewData vd = new ViewData(
                    shape,
                    0,
                    i * 4,  // Vertical offset for each preview
                    null
            );
            previews.add(vd);
        }

        return previews;
    }

    /**
     * Creates a point object to hold desired changes to Brick-shape-object's relative coordinates in playable area (currentGameMatrix).<br>
     * Calls intersect() to check if new desired position of Brick-shape-object is valid within the playable area. <br>
     * @return  If VALID -> else statement runs and currentOffset becomes the new coordinates and moveBrickDown() returns TRUE,
     * if INVALID, moveBrickDown() returns FALSE.
     */
    @Override
    public boolean moveBrickDown () { return moveBrick(0, 1); }

    /**
     * Creates a point object to hold desired changes to Brick-shape-object's relative coordinates in playable area (currentGameMatrix).<br>
     * Calls intersect() to check if new desired position of Brick-shape-object is valid within the playable area. <br>
     * @return  If VALID -> else statement runs and currentOffset becomes the new coordinates and moveBrickDown() returns TRUE,
     * if INVALID, moveBrickDown() returns FALSE.
     */
    @Override
    public boolean moveBrickLeft () { return moveBrick(-1, 0); }

    /**
     * Creates a point object to hold desired changes to Brick-shape-object's relative coordinates in playable area (currentGameMatrix).<br>
     * Calls intersect() to check if new desired position of Brick-shape-object is valid within the playable area. <br>
     * @return  If VALID -> else statement runs and currentOffset becomes the new coordinates and moveBrickDown() returns TRUE,
     * if INVALID, moveBrickDown() returns FALSE.
     */
    @Override
    public boolean moveBrickRight () { return moveBrick(1, 0); }

    /**
     * Takes in desired translation values and processes them via intersect() method call.
     * @param dx    x-axis translation.
     * @param dy    y-axis translation.
     * @return      TRUE if VALID, FALSE if INVALID.
     */
    private boolean moveBrick (int dx, int dy) {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(dx, dy);
        if (MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY())) {
            return false;
        }
        currentOffset = p;
        return true;
    }

    /**
     * Calls clockwise rotation logic in BrickRotater. <br>
     * Passes a RotationDirection based accordingly.
     * @return TRUE if VALID rotation, FALSE if INVALID rotation.
     */
    @Override
    public boolean rotateBrickRight () {return rotateBrick(RotationDirection.CLOCKWISE); }

    /**
     * ------------------------------------ADDED A ROTATE RIGHT METHOD------------------------------------<br>
     * Calls clockwise rotation logic in BrickRotater. <br>
     * Passes a RotationDirection based accordingly.
     * @return TRUE if VALID rotation, FALSE if INVALID rotation.
     */
    @Override
    public boolean rotateBrickLeft () { return rotateBrick(RotationDirection.ANTI_CLOCKWISE); }

    /**
     * ------------------------------------ADDED A GENERAL ROTATE METHOD------------------------------------<br>
     * Extracted logic out of rotateLeftBrick() and placed it in a new method rotateBrick(). <br>
     * Creates a nextShape object to hold the next orientation in the Brick-shape-object's brickMatrix List. <br>
     * Calls intersect() to check if the next orientation of the Brick-shape-object is valid within the playable area. <br>
     * @return  If VALID -> else statement runs and sets the Brick-shape-object's current orientation to the new desired orientation
     * and rotateBrickLeft() returns TRUE,
     * if INVALID, rotateBrickLeft() returns FALSE.
     */
    @Override
    public boolean rotateBrick (RotationDirection rd) {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        NextShapeInfo nextShape = brickRotator.nextRotation(rd);
        boolean conflict = MatrixOperations.intersect(currentMatrix, nextShape.shape(), (int) currentOffset.getX(), (int) currentOffset.getY());
        if (conflict) {
            return false;
        } else {
            brickRotator.setCurrentShape(nextShape.position());
            return true;
        }
    }

    @Override
    public DownData snapBrick (RefreshCoordinator refreshCoordinator, Rectangle[][] displayMatrix) {
        Point p = new Point(currentOffset);

        int targetY = MatrixOperations.findSnapPosition(
                currentGameMatrix,
                brickRotator.getCurrentShape(),
                (int) p.getX(),
                (int) p.getY()
        );

        getScore().add((targetY - currentOffset.y) * 5);
        currentOffset.y = targetY;
        mergeBrickToBackground();

        refreshCoordinator.renderBackground(currentGameMatrix, displayMatrix);
        ClearRow clearRow = clearRows();
//        THIS COSTED ME 1 DAY OF PROGRESS WALAO
        refreshCoordinator.renderBackground(currentGameMatrix, displayMatrix);

        if (clearRow.linesRemoved() > 0) { getScore().add(clearRow.scoreBonus()); }

        boolean gameOver = createNewBrick();
//        holdUsedThisTurn = false;
        ViewData vd = getViewData();

        return new DownData(clearRow, vd);
    }

    /**
     * ------------------------------------MIGHT BE ABLE TO CHANGE SPAWN POINT HERE------------------------------------<br>
     * Creates a new Brick object (currentBrick) by popping the first (top) Brick-shape-object from the Deque, nextBricks. <br>
     * Calls setBrick() to set the new Brick-shape-object's orientation to the first in its brickMatrix List. <br>
     * currentOffset is the coordinates in the playable area (currentGameMatrix) where the new Brick-shape-object will be generated,
     * AKA the spawn point. <br>
     * @return  If the spawn point is VALID, createNewBrick() returns FALSE,
     * if spawn point in INVALID, createNewBrick() returns TRUE.
     */
    @Override
    public boolean createNewBrick() {
        Brick currentBrick = brickGenerator.getBrick();
        brickRotator.setBrick(currentBrick);
        currentOffset = new Point(4, 1);
        return MatrixOperations.intersect(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    /**
     * @return  currentGameMatrix, the current game state with all the Brick objects in place.
     */
    @Override
    public int[][] getBoardMatrix() {
        return currentGameMatrix;
    }

    /**
     * @return  Creates a ViewData object with fields: current Brick-shape-object's current orientation, B-s-o's current x-coordinates,
     * B-s-o's current y-coordinates, the first orientation in the brickMatrix of the first (top) Brick-shape-object in the Deque, nextBricks.
     */
    @Override
    public ViewData getViewData() {
        return new ViewData (
                brickRotator.getCurrentShape(),
                (int) currentOffset.getX(),
                (int) currentOffset.getY(),
                brickGenerator.getNextBrick().getShapeMatrix().getFirst());
    }

    /**
     * Calls merge() to assimilate the current Brick-shape-object into the game's playable area (currentGameMatrix).
     */
    @Override
    public void mergeBrickToBackground() {
        holdUsedThisTurn = false;
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
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
        return clearRow;
    }

    /**
     * @return  Game score.
     */
    @Override
    public Score getScore() {
        return score;
    }

    /**
     * Creates a playable area (currentGameMatrix) with set values of width and height from constructor.<br>
     * Sets the game score to 0 (zero).<br>
     * Calls createNewBrick() to generate a new Brick from the top of the Deque, newBricks, at the spawn point.
     */
    @Override
    public void newGame() {
        currentGameMatrix = new int[width][height];
        score.reset();
        createNewBrick();
    }

    @Override
    public ViewData holdBrick () {
        if (holdUsedThisTurn) { return getViewData(); }

        Brick curr = brickRotator.getBrick();

        if (heldBrick == null) {
            heldBrick = curr;
            createNewBrick();
        }
        else {
            Brick tmp = heldBrick;
            heldBrick = curr;
            brickRotator.setBrick(tmp);
            currentOffset = new Point(4, 1);
        }

        holdUsedThisTurn = true;
        return getViewData();
    }

    @Override
    public Brick getHeldBrick () {
        return heldBrick;
    }
}
