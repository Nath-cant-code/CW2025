package com.comp2042.input.system_events;

/**
 * This class contains info on EventType (keystroke) and EventSource for checking and validation.
 */
public record MoveEvent(EventType eventType, EventSource eventSource) {
    /**
     * When an object of this class is initialised, an EventType and EventSource must be passed.
     *
     * @param eventType   Keystroke or direction.
     * @param eventSource User or system's automatic processing.
     */
    public MoveEvent {
    }

    /**
     * @return eventType.
     */
    @Override
    public EventType eventType() {
        return eventType;
    }

    /**
     * @return eventSource.
     */
    @Override
    public EventSource eventSource() {
        return eventSource;
    }
}
