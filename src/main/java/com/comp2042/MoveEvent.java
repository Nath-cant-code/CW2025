package com.comp2042;

/**
 * This class contains info on EventType (keystroke) and EventSource for checking and validation.
 */
public final class MoveEvent {
    private final EventType eventType;
    private final EventSource eventSource;

    /**
     * When an object of this class is initialised, an EventType and EventSource must be passed.
     * @param eventType     Keystroke or direction.
     * @param eventSource   User or system's automatic processing.
     */
    public MoveEvent(EventType eventType, EventSource eventSource) {
        this.eventType = eventType;
        this.eventSource = eventSource;
    }

    /**
     * @return  eventType.
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * @return eventSource.
     */
    public EventSource getEventSource() {
        return eventSource;
    }
}
