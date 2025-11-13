package com.comp2042;

/**
 * This class acts as one of the bridges (after GuiController) between player actions and the game logic
 * by creating methods that link the player's actions to the game's responses towards player actions.
 */
public class GameController implements InputEventListener {

    private Board board = new SimpleBoard(25, 10);
//    private Board board = new SimpleBoard(50, 20);

    private final GuiController viewGuiController;

    /**
     * Creates new brick via createNewBrick() method in SimpleBoard object.<br>
     * Player actions will cause the GuiController class to notify the viewGuiController object of this class. <br>
     * Opens a window in which the game will initialise with the state provided by getBoardMatrix (currentGameMatrix),
     * along with data about the Brick-shape-objects in the Deque, nextBricks.<br>
     * Automatically updates the score when a player earns points from clearing rows.
     * @param c GuiController created in start() in Main class.
     */
    public GameController(GuiController c) {
        viewGuiController = c;
        board.createNewBrick();
        viewGuiController.setEventListener(this);
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());
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
        if (!canMove) {
            board.mergeBrickToBackground();
            clearRow = board.clearRows();
            if (clearRow.getLinesRemoved() > 0) {
                board.getScore().add(clearRow.getScoreBonus());
            }
            if (board.createNewBrick()) {
                viewGuiController.gameOver();
            }

            viewGuiController.refreshGameBackground(board.getBoardMatrix());
//            Refresh.refreshGameBackground(board.getBoardMatrix(), viewGuiController.displayMatrix);

        } else {
            if (event.getEventSource() == EventSource.USER) {
                board.getScore().add(1);
            }
        }
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
     * Calls rotateLeftBrick() in SimpleBoard.
     * @param event MoveEvent object containing EventType (keystroke) and EventSource.
     * @return      ViewData object containing information on current Brick-shape-object after alterations from player action
     * and info on next Brick-shape-object.
     */
    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateLeftBrick();
        return board.getViewData();
    }


    /**
     * Calls newGame() in SimpleBoard.<br>
     * Update the game state of the playable area to an empty currentGameMatrix.
     */
    @Override
    public void createNewGame() {
        board.newGame();
//        Refresh.refreshGameBackground(board.getBoardMatrix(), viewGuiController.displayMatrix);
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
    }
}
