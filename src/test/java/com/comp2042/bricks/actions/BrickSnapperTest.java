package com.comp2042.bricks.actions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BrickSnapperTest {

    @Test
//    method should return the correct position after hard dropping Brick on empty floor
    void testFindSnapPosition_EmptyBoard_ReturnsBottom() {
        int[][] board = new int[10][10];
        int[][] brick = {{1, 1}, {1, 1}};

        int result = BrickSnapper.findSnapPosition(board, brick, 4, 0);

        assertEquals(8, result);
    }

    @Test
//    method should return correct position after hard dropping Brick on occupied space in playable area
    void testFindSnapPosition_WithObstacle_StopsBeforeObstacle() {
        int[][] board = new int[10][10];
        board[7][4] = 1;
        int[][] brick = {{1, 1}, {1, 1}};

        int result = BrickSnapper.findSnapPosition(board, brick, 4, 0);

        assertEquals(5, result);
    }

    @Test
//    method should return the current position if the Brick is already at a collision
    void testFindSnapPosition_AlreadyAtBottom_ReturnsSamePosition() {
        int[][] board = new int[10][10];
        board[9][4] = 1;
        int[][] brick = {{1, 1}, {1, 1}};

        int result = BrickSnapper.findSnapPosition(board, brick, 4, 7);

        assertEquals(7, result);
    }
}