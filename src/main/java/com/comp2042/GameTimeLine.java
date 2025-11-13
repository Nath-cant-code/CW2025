package com.comp2042;

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
     * @param gc
     */
    public void setGameTimeline (GuiController gc) {
//        this.timeline = new Timeline(new KeyFrame(Duration.millis(400), e -> onTick.run()));
//        timeline.setCycleCount(Timeline.INDEFINITE);
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
