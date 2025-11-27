package com.comp2042.input.event_controllers;

import com.comp2042.board.*;
import com.comp2042.board.composite_bricks.DownData;
import com.comp2042.board.composite_bricks.ViewData;
import com.comp2042.system_events.EventSource;
import com.comp2042.system_events.MoveEvent;
import com.comp2042.ui.ui_systems.GameView;

/**
 * This class acts as one of the bridges (after GuiController) between player actions and the game logic
 * by creating methods that link the player's actions to the game's responses towards player actions.
 */
public class GameController implements InputEventListener {
    private final Board board;
    private final GameView gameView;

    /**
     * -----------------------------NEW-------------------------------<br>
     * SOLID: Dependency Injection<br>
     * Similar to how in the original code, Board board = new SimpleBoard was used,
     * I have made a change to mirror that, i.e. GameView gameView = new GuiController <br>
     * -----------------------------NEW-------------------------------<br>
     * Creates new brick via createNewBrick() method in SimpleBoard object.<br>
     * Player actions will cause the GuiController class to notify the viewGuiController object of this class. <br>
     * Opens a window in which the game will initialise with the state provided by getBoardMatrix (currentGameMatrix),
     * along with data about the Brick-shape-objects in the Deque, nextBricks.<br>
     * Automatically updates the score when a player earns points from clearing rows.
     * @param gameView GameView > GuiController object created in start() in Main class.
     */
    public GameController(GameView gameView, Board board) {
        this.board = board;
        this.gameView = gameView;

        board.createNewBrick();
        gameView.setEventListener(this);
        gameView.initialise(board.getBoardMatrix(), board.getViewData());
        gameView.bindScore(board.getScore().scoreProperty());
        gameView.bindLevel(board.getLevelSystem().currentLevelProperty());
    }

    /**
     * Convenience constructor that creates a default SimpleBoard.<br>
     * Used for backward compatibility.
     */
    public GameController(GameView gameView) {
        this(gameView, new SimpleBoard(25, 10));
    }

    /**
     * Calls moveBrickDown() from SimpleBoard which returns TRUE if movement is VALID, FALSE if INVALID.<br>
     * <p>
     *     In the case of TRUE (VALID), go to else statement and check if the downwards movement is from the player (USER)
     *     and not the system's automatic natural falling mechanism (THREAD).<br>
     *     If the movement is from the player, add a single point to their overall score.
     * </p>
     * <p>
     *     In the case of FALSE (INVALID), DISALLOW the movement by calling mergeBrickToBackground()
     *     and check if the result of merging the Brick-shape-object completely fills a row. <br>
     *     If there is at least one row being cleared, get the points obtained and add it to the overall score.<br>
     *     Also check if a new Brick object can be generated at the spawn point after merging the previous Brick object
     *     with the playable area, if not then stop the game.<br>
     *     Update the game state of the playable area (currentGameMatrix) at the end of everything.
     * </p>
     *
     * @param event The player's action (keystroke) that dictates a downwards movement on the Brick object.
     * @return      A DownData object with ClearRow object and ViewData object.
     */
    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBrickDown();
        ClearRow clearRow = null;

        System.out.println("canmove: " + canMove);
        if (!canMove) {
            board.mergeBrickToBackground();
            clearRow = board.clearRows();

            System.out.println("lines removed = " + clearRow.linesRemoved());

            if (clearRow.linesRemoved() > 0) {
                System.out.println("clearrow ran");
                board.getScore().add(clearRow.scoreBonus());
                gameView.showClearRowNotification(clearRow.scoreBonus());

                boolean leveledUp = board.getLevelSystem().addClearedRows(
                        clearRow.linesRemoved()
                );

                System.out.println("GC LevelSystem hash: " + board.getLevelSystem());

                System.out.println("leveledUp: " + leveledUp);

                if (leveledUp) {
                    System.out.println("Game level up");
                    int newLevel = board.getLevelSystem().getCurrentLevel();
                    gameView.notifyLevelUp(newLevel);
                    gameView.updateFallSpeed(board.getLevelSystem().getFallSpeedMs());
                }
            }

            if (board.createNewBrick()) {
                gameView.notifyGameOver();
            }

            gameView.refreshBackground(board.getBoardMatrix());

        }
        else {
            if (event.eventSource() == EventSource.USER) {
                board.getScore().add(1);
            }
        }
        gameView.refreshActiveBrick(board.getViewData());
        gameView.refreshPreviewPanel();
        return new DownData(clearRow, board.getViewData());
    }

    /**
     * Calls moveBrickLeft() in SimpleBoard.
     * @param event MoveEvent object containing EventType (keystroke) and EventSource.
     * @return      ViewData object containing information on current Brick-shape-object after alterations from player action
     * and info on next Brick-shape-object.
     */
    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        return board.getViewData();
    }

    /**
     * Calls moveBrickRight() in SimpleBoard.
     * @param event MoveEvent object containing EventType (keystroke) and EventSource.
     * @return      ViewData object containing information on current Brick-shape-object after alterations from player action
     * and info on next Brick-shape-object.
     */
    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        return board.getViewData();
    }

    /**
     * Connects InputHandler to SimpleBoard + BrickRotater.
     * @param event MoveEvent object containing EventType (keystroke) and EventSource.
     * @return      ViewData object containing information on current Brick-shape-object after alterations from player action
     * and info on next Brick-shape-object.
     */
    @Override
    public ViewData onRotateClock(MoveEvent event) {
        board.rotateBrickRight();
        return board.getViewData();
    }

    /**
     * Connects InputHandler to SimpleBoard + BrickRotater.
     * @param event MoveEvent object containing EventType (keystroke) and EventSource.
     * @return      ViewData object containing information on current Brick-shape-object after alterations from player action
     * and info on next Brick-shape-object.
     */
    @Override
    public ViewData onRotateAntiClock(MoveEvent event) {
        board.rotateBrickLeft();
        return board.getViewData();
    }

    /**
     * Calls method to hard drop the current Brick to the bottom.
     * @param event MoveEvent object containing EventType (keystroke) and EventSource.
     * @return ViewData object containing information on current Brick-shape-object after alterations from player action
     * and info on next Brick-shape-object.
     */
    @Override
    public ViewData onSnapEvent(MoveEvent event) {
        DownData downData = board.snapBrick(
                            ((com.comp2042.ui.ui_systems.GuiController) gameView).refreshCoordinator,
                            gameView.getDisplayMatrix()
        );

        if (downData.clearRow().linesRemoved() > 0) {
            System.out.println("clearrow ran");
            board.getScore().add(downData.clearRow().scoreBonus());
            gameView.showClearRowNotification(downData.clearRow().scoreBonus());

            boolean leveledUp = board.getLevelSystem().addClearedRows(
                    downData.clearRow().linesRemoved()
            );

            System.out.println("GC LevelSystem hash: " + board.getLevelSystem());

            System.out.println("leveledUp: " + leveledUp);

            if (leveledUp) {
                System.out.println("Game level up");
                int newLevel = board.getLevelSystem().getCurrentLevel();
                gameView.notifyLevelUp(newLevel);
                gameView.updateFallSpeed(board.getLevelSystem().getFallSpeedMs());
            }
        }

        return board.getViewData();
    }

    /**
     * Refreshes the hold panel to contain the selected held Brick.
     */
    @Override
    public void onHoldEvent () {
        if (board == null) return;

        board.holdBrick();
        ViewData viewData = board.getViewData();
//        gameView.refreshHoldPanel(viewData, board.getHeldBrick());
        gameView.refreshHoldBrick();
        gameView.refreshActiveBrick(viewData);
    }

    /**
     * Calls newGame() in SimpleBoard.<br>
     * Update the game state of the playable area to an empty currentGameMatrix.
     */
    @Override
    public void createNewGame() {
        board.newGame();
        gameView.refreshBackground(board.getBoardMatrix());
    }
}
