//package com.comp2042;
//
//import javafx.beans.property.BooleanProperty;
//import javafx.scene.layout.GridPane;
//import javafx.scene.shape.Rectangle;
//
//public class Refresh {
//
//    public static void refreshBrick (ViewData brick, Rectangle[][] rectangles, GridPane brickPanel, GridPane gamePanel) {
//        if (GuiController.isPause.getValue() == Boolean.FALSE) {
//            brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap()
//                    + brick.getxPosition() * GuiController.BRICK_SIZE);
//            brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap()
//                    + brick.getyPosition() * GuiController.BRICK_SIZE);
//            for (int i = 0; i < brick.getBrickData().length; i++) {
//                for (int j = 0; j < brick.getBrickData()[i].length; j++) {
//                    setRectangleData(brick.getBrickData()[i][j], rectangles[i][j]);
//                }
//            }
//        }
//    }
//
//    public static void refreshGameBackground(int[][] board, Rectangle[][] displayMatrix) {
//        for (int i = 2; i < board.length; i++) {
//            for (int j = 0; j < board[i].length; j++) {
//                setRectangleData(board[i][j], displayMatrix[i][j]);
//            }
//        }
//    }
//
//    public static void setRectangleData(int color, Rectangle rectangle) {
//        rectangle.setFill(GuiController.getFillColor(color));
//        rectangle.setArcHeight(9);
//        rectangle.setArcWidth(9);
//    }
//}
