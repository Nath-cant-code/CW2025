package com.comp2042.ui.systems.initializers;

import com.comp2042.logic.game_records.ViewData;
import com.comp2042.input.keyboard.InputHandler;
import com.comp2042.input.event_listener.InputEventListener;
import com.comp2042.renderer.basic_renderers.BoardRenderer;
import com.comp2042.renderer.basic_renderers.BrickRenderer;
import com.comp2042.ui.systems.master.GameView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

/**
 * Initializes game view components.<br>
 * SOLID: Single Responsibility - Only initializes game view<br>
 */
public class GameInitializer {
    private static final int BRICK_SIZE = 20;

    public Rectangle[][] initialiseBoard(GridPane gamePanel, int[][] boardMatrix) {
        BoardRenderer boardRenderer = new BoardRenderer();
        return boardRenderer.createPlayableAreaMatrix(gamePanel, boardMatrix);
    }

    public Rectangle[][] initialiseBrickArea(GridPane brickPanel, ViewData brick) {
        BrickRenderer brickRenderer = new BrickRenderer();
        return brickRenderer.createBrickAreaMatrix(brickPanel, brick);
    }

    public void positionBrickPanel(GridPane brickPanel, GridPane gamePanel, ViewData brick) {
        brickPanel.setLayoutX(gamePanel.getLayoutX() +
                brick.xPosition() * brickPanel.getVgap() +
                brick.xPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() +
                brick.yPosition() * brickPanel.getHgap() +
                brick.yPosition() * BRICK_SIZE);
    }

    public InputHandler createInputHandler(GameView gc,
                                           InputEventListener eventListener,
                                           Rectangle[][] rectangles,
                                           GridPane brickPanel,
                                           GridPane gamePanel) {
        InputHandler inputHandler = new InputHandler(
                gc,
                eventListener,
                rectangles,
                brickPanel,
                gamePanel
        );
        inputHandler.setKeyListener();
        return inputHandler;
    }
}