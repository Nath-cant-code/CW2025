package com.comp2042.ui.elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LevelSystemTest {

    private LevelSystem levelSystem;

    @BeforeEach
    void setUp() {
        levelSystem = new LevelSystem();
    }

    @Test
//    checks that the starting level is always 1
    void testInitialLevel_IsOne() {
        assertEquals(1, levelSystem.getCurrentLevel());
    }

    @Test
//    method should return true if number of cleared rows requirement for level up is achieved
    void testAddClearedRows_LevelsUp_ReturnsTrue() {
        boolean leveledUp = levelSystem.addClearedRows(5);

        assertTrue(leveledUp);
        assertEquals(2, levelSystem.getCurrentLevel());
    }

    @Test
//    method should return false if there is still an insufficient number of cleared rows for level up
    void testAddClearedRows_NotEnoughToLevelUp_ReturnsFalse() {
        boolean leveledUp = levelSystem.addClearedRows(3);

        assertFalse(leveledUp);
        assertEquals(1, levelSystem.getCurrentLevel());
    }

    @Test
//    ensures the levels do not go beyond 10
    void testAddClearedRows_MaxLevel_StopsAtMax() {
        levelSystem.addClearedRows(50);

        assertEquals(10, levelSystem.getCurrentLevel());
    }

    @Test
//    ensures that speed is increased on each level
    void testGetFallSpeedMs_IncreasesWithLevel() {
        int initialSpeed = levelSystem.getFallSpeedMs();

        levelSystem.addClearedRows(5);
        int newSpeed = levelSystem.getFallSpeedMs();

        assertTrue(newSpeed < initialSpeed);
    }

    @Test
//    ensures that levels are reset to 1 when needed
//    ensures that counter for rows cleared is reset as well
    void testReset_ResetsToLevelOne() {
        levelSystem.addClearedRows(10);
        levelSystem.reset();

        assertEquals(1, levelSystem.getCurrentLevel());
        assertEquals(0, levelSystem.getTotalRowsCleared());
    }
}