package com.comp2042.input.event_controllers;

import com.comp2042.board.composite_bricks.DownData;
import com.comp2042.board.composite_bricks.ViewData;
import com.comp2042.system_events.MoveEvent;

/**
 * Interface implemented by GameController.
 */
public interface InputEventListener {

    DownData onDownEvent(MoveEvent event);

    ViewData onLeftEvent(MoveEvent event);

    ViewData onRightEvent(MoveEvent event);

    ViewData onRotateClock(MoveEvent event);

    ViewData onRotateAntiClock(MoveEvent event);

    DownData onSnapEvent(MoveEvent event);

    void onHoldEvent();

    void createNewGame();
}
