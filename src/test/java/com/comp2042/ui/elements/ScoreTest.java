package com.comp2042.ui.elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {

    private Score score;

    @BeforeEach
    void setUp() {
        score = new Score();
    }

    @Test
//    ensures the score is initialised at 0
    void testInitialScore_IsZero() {
        assertEquals(0, score.scoreProperty().get());
    }

    @Test
//    ensure that the score added tallies with what is stored
    void testAdd_IncreasesScore() {
        score.add(100);

        assertEquals(100, score.scoreProperty().get());
    }

    @Test
//    ensures that scores are calculated correctly
    void testAdd_MultipleAdds_Accumulates() {
        score.add(50);
        score.add(75);
        score.add(25);

        assertEquals(150, score.scoreProperty().get());
    }

    @Test
//    ensures that the score is reset to zero when needed
    void testReset_SetsScoreToZero() {
        score.add(500);
        score.reset();

        assertEquals(0, score.scoreProperty().get());
    }
}