package com.comp2042.ui;

import com.comp2042.system_events.EventSource;
import com.comp2042.system_events.EventType;
import com.comp2042.system_events.MoveEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * This class is to separate TimeLine object creation from GuiController.java class.
 */
public class GameTimeLine {
    private Timeline timeLine;

    /**
     * Creates a timeline object that automatically causes Brick objects to naturally fall at specific intervals, Duration,millis( x ).
     * @param gc    GuiController object to call methods.
     */
    public void setGameTimeline (GuiController gc) {
        timeLine = new Timeline(new KeyFrame(
                Duration.millis(400),
                ae -> gc.moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
    }
    public void start() {
        timeLine.play();
    }

    public void stop() {
        timeLine.stop();
    }
}
