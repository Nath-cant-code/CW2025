package com.comp2042.input.event_controllers;

import com.comp2042.board.*;
import com.comp2042.board.composite_bricks.DownData;
import com.comp2042.board.composite_bricks.ViewData;
import com.comp2042.system_events.EventSource;
import com.comp2042.system_events.MoveEvent;
import com.comp2042.ui.ui_systems.GameView;

/**
 * -----------------------------------REFACTORED-----------------------------------<br>
 * This class contains onEvent() methods connected to game's keybinds in KeyBindingManager.<br>
 * onEvent() methods call corresponding methods in other classes that contain the logic for the respective events.<br>
 * SOLID: Single Responsibility: Only has onEvent() methods.
 * SOLID: Dependency Injection:<br>
 * Similar to how in the original code, Board board = new SimpleBoard was used,
 * I have made a change to mirror that, i.e. GameView gameView = new GuiController <br>
 * Design Pattern: Facade: Calls the corresponding delegated methods. Class methods do not contain logic for the events.
 */
public class GameController implements InputEventListener {
    private final Board board;
    private final GameView gameView;
    private final BrickMergeProcessor mergeProcessor;

    public GameController(GameView gameView, Board board) {
        this.board = board;
        this.gameView = gameView;
        this.mergeProcessor = new BrickMergeProcessor(board, gameView);

        board.createNewBrick();
        gameView.setEventListener(this);
        gameView.initGameView(board.getBoardMatrix(), board.getViewData());
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
     *     In the case of FALSE (INVALID), DISALLOW the movement by calling mergeBrickToBackground().<br>
     *     processMerge() is called to handle processing the state of the playable area and evaluate the condition of the state,
     *     i.e. to clear rows, or if the special shape is formed.
     * </p>
     *
     * @param event The player's action (keystroke) that dictates a downwards movement on the Brick object.
     * @return      A DownData object with ClearRow object and ViewData object.
     */
    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBrickDown();
        ClearRow clearRow = null;

        if (!canMove) {
//            2 hours spent here on the 2 refresh methods
            gameView.refreshActiveBrick(board.getViewData());
            gameView.refreshBackground(board.getBoardMatrix());
            board.mergeBrickToBackground();

            clearRow = mergeProcessor.processMerge();
        }
        else {
            if (event.eventSource() == EventSource.USER) {
                board.getScore().add(1);
            }
        }

//        IMPORTANT: THESE REFRESH CALLS CANNOT BE REMOVED
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
     * Calls rotateBrickRight() in SimpleBoard.
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
     * Calls rotateBrickLeft() in SimpleBoard.
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
     * Calls method to hard drop the current Brick to the bottom then merges brick to background.
     * Calls processMerge() handle processing the state of the playable area and evaluate the condition of the state,
     * i.e. to clear rows, or if the special shape is formed.
     * @param event MoveEvent object containing EventType (keystroke) and EventSource.
     * @return A DownData object with ClearRow object and ViewData object.
     */
    @Override
    public DownData onSnapEvent(MoveEvent event) {
        board.snapBrick(
                ((com.comp2042.ui.ui_systems.GuiController) gameView).refreshCoordinator,
                gameView.getDisplayMatrix()
        );

//        2 hours spent here on the 2 refresh methods
        gameView.refreshActiveBrick(board.getViewData());
        gameView.refreshBackground(board.getBoardMatrix());
        board.mergeBrickToBackground();

        ClearRow clearRow = mergeProcessor.processMerge();

        return new DownData(clearRow, board.getViewData());
    }

    /**
     * Calls holdBrick() method.
     * Refreshes the hold panel to contain the selected held Brick.
     */
    @Override
    public void onHoldEvent () {
        if (board == null) return;

        board.holdBrick();
        ViewData viewData = board.getViewData();
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
