package com.comp2042.ui.systems.master;

import com.comp2042.logic.game_records.ViewData;
import com.comp2042.bricks.production.blueprints.AbstractBrick;
import com.comp2042.ui.panels.LevelUpPanel;
import javafx.beans.property.IntegerProperty;

/**
 * ----------------------------------------REFACTORED----------------------------------------<br>
 * SOLID: Single Responsibility: Coordinates UI methods that are constantly called<br>
 * SOLID: Open Closed: New functionalities can be added in GUIController without affecting fixed handling of UI in GUIManager<br>
 * SOLID: Liskov Substitution: GUIController is a subclass that extends GUIManager.<br>
 * SOLID: Dependency Inversion: Objects of GameView are created and depended on instead of depending on details in this class<br>
 * Design Pattern: Facade: Simple interface to complex UI subsystem<br>
 */
public class GUIController extends GUIManager {

    @Override
    public void notifyLevelUp(int newLevel) {
        LevelUpPanel levelUpPanel = new LevelUpPanel(newLevel);
        groupNotification.getChildren().add(levelUpPanel);
        levelUpPanel.showLevelUp(groupNotification.getChildren());
    }

    /**
     * Calls method to update (increase) fall speed
     * @param speedMs   Current falling speed of Brick object
     */
    @Override
    public void updateFallSpeed(int speedMs) {
        timeLineManager.updateSpeed(speedMs, eventListener);
    }

    /**
     * Binds level text to level label at the side
     * @param levelProperty Current level
     */
    @Override
    public void bindLevel(IntegerProperty levelProperty) {
        levelLabel.textProperty().bind(levelProperty.asString("Level: %d"));
    }

    /**
     * Calls refresh Brick method
     * @param viewData  Brick object info
     */
    @Override
    public void refreshActiveBrick (ViewData viewData) {
        refreshCoordinator.renderActiveBrick(viewData, rectangles, brickPanel, gamePanel);
    }

    /**
     * Concise method to update (refresh) background.
     * @param boardMatrix The current board state.
     */
    @Override
    public void refreshBackground(int[][] boardMatrix) {
        refreshCoordinator.renderBackground(boardMatrix, displayMatrix);
    }

    /**
     * Calls render method for rendering Brick object in hold panel.
     */
    @Override
    public void refreshHoldBrick () {
        refreshCoordinator.renderHoldBrick((AbstractBrick) board.getHeldBrick(), holdMatrix, holdPanel);
    }

    /**
     * Calls render method for rendering Brick objects in queue in preview panel.
     */
    @Override
    public void refreshPreviewPanel () {
        refreshCoordinator.renderNextBricks(board.getNextBricksPreview(), previewMatrix);
    }

    /**
     * Calls method containing the logic that handles UI for when special combination shape is formed
     */
    @Override
    public void handleSpecialShapeCompletion() {
        specialShapeManager.handleCompletion(rectangles, this, groupNotification.getChildren());
    }

    /**
     * Hides display panel of the special combination shape
     */
    @Override
    public void hideSpecialShapeDisplay() {
        if (specialShapeContainer != null && specialShapeDisplayPanel != null) {
            specialShapeContainer.getChildren().remove(specialShapeDisplayPanel);
        }
    }

    /**
     * Checks if the display panel of the special combination shape is visible
     * @return TRUE if there are elements in specialShapeContainer
     */
    @Override
    public boolean isSpecialShapeDisplayVisible() {
        return specialShapeContainer != null &&
                specialShapeContainer.getChildren().contains(specialShapeDisplayPanel);
    }
}
