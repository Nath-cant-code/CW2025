package com.comp2042;

/**
 * Interface implemented by GameController.
 */
public interface InputEventListener {

    DownData onDownEvent(MoveEvent event);

    ViewData onLeftEvent(MoveEvent event);

    ViewData onRightEvent(MoveEvent event);

    ViewData onRotateClock(MoveEvent event);

    ViewData onRotateAntiClock(MoveEvent event);

    void createNewGame();
}
