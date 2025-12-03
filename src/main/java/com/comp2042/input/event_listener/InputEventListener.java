package com.comp2042.input.event_listener;

import com.comp2042.logic.game_records.DownData;
import com.comp2042.logic.game_records.ViewData;
import com.comp2042.input.system_events.MoveEvent;

/**
 * Interface implemented by EventListener.<br>
 * Method explanations in EventListener.java.
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
