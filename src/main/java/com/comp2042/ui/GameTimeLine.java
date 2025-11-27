package com.comp2042.ui;

import com.comp2042.system_events.EventSource;
import com.comp2042.system_events.EventType;
import com.comp2042.system_events.MoveEvent;
import com.comp2042.ui.ui_systems.GuiController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * This class is to separate TimeLine object creation from GuiController.java class.<br>
 * Also manages increasing fall speed with level progressions.<br>
 * SOLID: Single Responsibility - Only manages game timeline
 */
public class GameTimeLine {
    private Timeline timeLine;
    private int currentSpeed = 400;

    public void setGameTimeline(GuiController gc) {
        createTimeline(gc, currentSpeed);
    }

    /**
     * Creates a timeline object that automatically causes Brick objects to naturally fall at specific intervals, Duration,millis( x ).
     * @param gc    GuiController object to call methods.
     */
    public void createTimeline (GuiController gc, int currSpeed) {
        if (timeLine != null) { timeLine.stop(); }

        timeLine = new Timeline(new KeyFrame(
                Duration.millis(currSpeed),
                ae -> gc.moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Updates the fall speed and recreates timeline.
     * @param newSpeed New fall speed in milliseconds
     * @param gc GuiController reference
     */
    public void updateSpeed(int newSpeed, GuiController gc) {
        boolean wasPlaying = timeLine != null &&
                timeLine.getStatus() == Timeline.Status.RUNNING;
        currentSpeed = newSpeed;
        createTimeline(gc, newSpeed);

        if (wasPlaying) { start(); }
    }

    /**
     * Starts game's timeline.
     */
    public void start() {
        if (timeLine != null) { timeLine.play(); }
    }

    /**
     * Stops game's timeline.
     */
    public void stop() {
        if (timeLine != null) { timeLine.stop(); }
    }
}
