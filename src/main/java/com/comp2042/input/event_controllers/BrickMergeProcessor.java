package com.comp2042.input.event_controllers;

import com.comp2042.board.Board;
import com.comp2042.board.ClearRow;
import com.comp2042.ui.ui_systems.GameView;
import java.awt.Point;

/**
 * Processes logic of sequence of method callings after a brick merges with the background.<br>
 * Especially important for downward event methods such as onDownEvent() and onSnapEvent().<br>
 * SOLID: Single Responsibility - Only handles post-merge processing<br>
 * Design Pattern: Template Method - Defines merge processing algorithm<br>
 */
public class BrickMergeProcessor {
    private final Board board;
    private final GameView gameView;

    public BrickMergeProcessor(Board board, GameView gameView) {
        this.board = board;
        this.gameView = gameView;
    }

    /**
     * Processes the complete merge sequence of downward events
     * such as onDownEvent() and onSnapEvent().<br>
     * Template Method pattern - defines the algorithm structure.<br>
     * @return ClearRow data from processing
     */
    public ClearRow processMerge() {
//        Step 1: Check for special shape
        if (checkSpecialShape()) {
            return new ClearRow(0, board.getBoardMatrix(), 0);
        }

//        Step 2: Clear rows
        ClearRow clearRow = board.clearRows();

//        Step 3: Handle row clearing
        handleRowClearing(clearRow);

//        Step 4: Create new brick
        handleNewBrick();

//        Step 5: Refresh display
        refreshDisplay();

        return clearRow;
    }

    /**
     * Checks for special shape completion.
     * @return true if special shape found and handled
     */
    private boolean checkSpecialShape() {
        if (board.isSpecialShapeCompleted()) {
            return false;
        }

        Point shapeLocation = board.checkSpecialShape();
        if (shapeLocation != null) {
            handleSpecialShape();
            return true;
        }

        return false;
    }

    /**
     * Handles special shape completion.
     */
    private void handleSpecialShape() {
        gameView.handleSpecialShapeCompletion();
        board.markSpecialShapeCompleted();

        if (board.createNewBrick()) {
            gameView.notifyGameOver();
        }

        gameView.refreshActiveBrick(board.getViewData());
        gameView.refreshPreviewPanel();
    }

    /**
     * Handles row clearing logic.
     */
    private void handleRowClearing(ClearRow clearRow) {
        if (clearRow.linesRemoved() > 0) {
            board.getScore().add(clearRow.scoreBonus());
            gameView.showClearRowNotification(clearRow.scoreBonus());

            boolean leveledUp = board.getLevelSystem().addClearedRows(
                    clearRow.linesRemoved()
            );

            if (leveledUp) {
                int newLevel = board.getLevelSystem().getCurrentLevel();
                gameView.notifyLevelUp(newLevel);
                gameView.updateFallSpeed(board.getLevelSystem().getFallSpeedMs());
            }
        }
    }

    /**
     * Handles new brick creation.
     */
    private void handleNewBrick() {
        if (board.createNewBrick()) {
            gameView.notifyGameOver();
        }
    }

    /**
     * Refreshes the display.
     */
    private void refreshDisplay() {
        gameView.refreshBackground(board.getBoardMatrix());
        gameView.refreshActiveBrick(board.getViewData());
        gameView.refreshPreviewPanel();
    }
}