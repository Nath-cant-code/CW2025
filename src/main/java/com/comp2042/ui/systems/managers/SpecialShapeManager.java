package com.comp2042.ui.systems.managers;

import com.comp2042.logic.engine.Board;
import com.comp2042.ui.elements.SpecialShapeConfig;
import com.comp2042.ui.panels.SpecialShapeTextPanel;
import com.comp2042.ui.systems.master.GameView;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

/**
 * Manages special shape completion logic and animations.<br>
 * SOLID: Single Responsibility - Only handles special shape completion<br>
 */
public class SpecialShapeManager {
    private final Board board;
    private final TimeLineManager timeLineManager;
    private final GameStateProperty gameStateProperty;
    private final GridPane gamePanel;

    public SpecialShapeManager(
            Board board,
            TimeLineManager timeLineManager,
            GameStateProperty gameStateProperty,
            GridPane gamePanel) {
        this.board = board;
        this.timeLineManager = timeLineManager;
        this.gameStateProperty = gameStateProperty;
        this.gamePanel = gamePanel;
    }

    /**
     * Handles the complete special shape completion sequence.
     * @param rectangles The active brick rectangles to clear
     * @param gc Needed to access methods in gc
     * @param groupNotificationList The notification group for displaying messages
     */
    public void handleCompletion(Rectangle[][] rectangles, GameView gc, ObservableList<Node> groupNotificationList) {
        timeLineManager.stop();
        gameStateProperty.setPaused(true);

//        Wait briefly to show the cleared board
        javafx.animation.PauseTransition pause =
                new javafx.animation.PauseTransition(
                        javafx.util.Duration.millis(SpecialShapeConfig.PAUSE_DURATION_MS)
                );

//        Show completion message
//        Reset playable area visuals
        pause.setOnFinished(e -> {
            board.clearEntireBoard();

//            this is required to visually remove the last Brick object that completes the special shape
            for (Rectangle[] rectangle : rectangles) {
                for (Rectangle value : rectangle) {
                    value.setFill(javafx.scene.paint.Color.TRANSPARENT);
                }
            }

//            Refresh to an empty playable area
            gc.refreshBackground(board.getBoardMatrix());

            board.getScore().add(SpecialShapeConfig.BONUS_POINTS);

            SpecialShapeTextPanel completionPanel = new SpecialShapeTextPanel();
            groupNotificationList.add(completionPanel);
            completionPanel.showCompletion(groupNotificationList);

//            Hide the special shape display panel
            gc.hideSpecialShapeDisplay();

//            Resume game after message displays
            javafx.animation.PauseTransition resumePause =
                    new javafx.animation.PauseTransition(
                            javafx.util.Duration.millis(SpecialShapeConfig.MESSAGE_DURATION_MS + 1000)
                    );

            resumePause.setOnFinished(ev -> {
                gameStateProperty.setPaused(false);
                timeLineManager.start();
                gamePanel.requestFocus();
            });

            resumePause.play();
        });

        pause.play();
    }
}