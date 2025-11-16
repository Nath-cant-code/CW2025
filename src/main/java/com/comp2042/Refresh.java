package com.comp2042;

import com.comp2042.bricks.AbstractBrick;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Separates rendering of background, Brick objects, and Brick colouring into a another file from GuiController.
 */
public class Refresh {
    private final GuiController gc;

    /**
     * @param gc    GuiController object to use its fields.
     */
    public Refresh (GuiController gc) {
        this.gc = gc;
    }

    /**
     * Only has functionality if game is not paused.<br>
     * Updates the position of the Brick object everytime is naturally falls.<br>
     * Hence, this method has to recolor the orientation of the Brick-shape-object inside the Rectangle box object it resides in.<br>
     * Is also called when the player does any action on the Brick object.<br>
     * @param brick         Info on current and next in line Brick-shape-object.
     * @param rectangles    Box area matrix.
     * @param brickPanel    brickPanel.
     * @param gamePanel     gamePanel.
     */
    public void refreshBrick (ViewData brick, Rectangle[][] rectangles, GridPane brickPanel, GridPane gamePanel) {
        if (gc.isPause.getValue() == Boolean.FALSE) {
            brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.xPosition() * brickPanel.getVgap()
                    + brick.xPosition() * GuiController.BRICK_SIZE);
            brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.yPosition() * brickPanel.getHgap()
                    + brick.yPosition() * GuiController.BRICK_SIZE);
            for (int i = 0; i < brick.brickData().length; i++) {
                for (int j = 0; j < brick.brickData()[i].length; j++) {
                    setRectangleData(brick.brickData()[i][j], rectangles[i][j]);
                }
            }
        }
    }

    /**
     * Recolours the displayMatrix to match currentGameMatrix.
     * @param board             Matrix of playable area (currentGameMatrix in SimpleBoard).
     * @param displayMatrix     Playable area matrix.
     */
    public void refreshGameBackground(int[][] board, Rectangle[][] displayMatrix) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }

    public void drawHoldBrick(AbstractBrick holdBrick) {
        GridPane holdPanel = gc.holdPanel;
        Rectangle[][] holdMatrix = gc.holdMatrix;

        for (int i = 0; i < holdMatrix.length; i++) {
            for (int j = 0; j < holdMatrix[i].length; j++) {
                setRectangleData(0, holdMatrix[i][j]);
            }
        }

        if (holdBrick == null) {
            return;
        }

        int[][] shape = holdBrick.getShapeMatrix().getFirst();
        int shapeWidth = shape.length;
        int shapeHeight = shape[0].length;

        int panelWidth = holdMatrix.length;
        int panelHeight = holdMatrix[0].length;

        int offsetX = (panelWidth - shapeWidth) / 2;
        int offsetY = (panelHeight - shapeHeight) / 2;

        for (int x = 0; x < shapeWidth; x++) {
            for (int y = 0; y < shapeHeight; y++) {

                int color = shape[x][y];
                if (color != 0) {
                    int drawX = x + offsetX;
                    int drawY = y + offsetY;

                    if (drawX >= 0 && drawX < panelWidth &&
                            drawY >= 0 && drawY < panelHeight) {

                        setRectangleData(color, holdMatrix[drawX][drawY]);
                    }
                }
            }
        }
    }

    /**
     * setRectangleData(src, dest).<br>
     * Updates the colour of each pixel of object passed as dest to match the colour of src.
     * @param color    A single pixel of board (in this class) (AKA playable area, currentGameMatrix) or a Brick-shape-object.
     * @param rectangle A single pixel displayMatrix.
     */
    public void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }

    /**
     * Sets a colour for the Brick-shape-object.
     * @param i Index to choose colour.
     * @return  Color object.
     */
    protected static Paint getFillColor(int i) {
        return switch (i) {
            case 0 -> Color.TRANSPARENT;
            case 1 -> Color.AQUA;
            case 2 -> Color.BLUEVIOLET;
            case 3 -> Color.DARKGREEN;
            case 4 -> Color.YELLOW;
            case 5 -> Color.RED;
            case 6 -> Color.BEIGE;
            case 7 -> Color.BURLYWOOD;
            default -> Color.WHITE;
        };
    }
}