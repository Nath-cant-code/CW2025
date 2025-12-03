package com.comp2042.input.keyboard;

import com.comp2042.input.system_events.MoveEvent;

/**
 * Represents a single input action that can be executed.<br>
 * Think of this class as InputExecutable.<br>
 * SOLID: Open/Closed Principle<br>
 * <p>
 *     - New input actions can be added without modifying existing code<br>
 *     - Just create new implementations of this interface<br>
 * </p>
 * Design Pattern: Command Pattern<br>
 * <p>
 *     - Encapsulates a request as an object<br>
 *     - Allows parameterization of clients with different requests<br>
 * </p>
 */
@FunctionalInterface
public interface InputAction {
    /**
     * Executes the action associated with this input.
     * @param event The movement event containing input details
     */
    void execute(MoveEvent event);
}