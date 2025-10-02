// Modified by Roo Code AI on 2025-10-02 with model: gpt-oss-120b
package com.example.trafficlightsimulator;

import com.example.trafficlightsimulator.exception.InvalidConfigurationException;
import com.example.trafficlightsimulator.model.LightColor;
import com.example.trafficlightsimulator.service.TrafficLightContext;
import com.example.trafficlightsimulator.util.ValidationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the traffic‑light simulator.
 *
 * Covers validation, state transitions, and business constraints.
 */
class TrafficLightSimulatorTest {

    private static final int RED_DURATION = 4;
    private static final int AMBER_DURATION = 1;
    private static final int GREEN_DURATION = 10;
    private static final int TOTAL_TIME = 30;

    @BeforeEach
    void setUp() {
        // No shared mutable state needed.
    }

    @Test
    void validConfigurationShouldPassValidation() {
        assertDoesNotThrow(() ->
                ValidationUtil.validateDurations(RED_DURATION, AMBER_DURATION, GREEN_DURATION, TOTAL_TIME));
    }

    @Test
    void amberGreaterThanRedShouldFailValidation() {
        InvalidConfigurationException ex = assertThrows(InvalidConfigurationException.class, () ->
                ValidationUtil.validateDurations(RED_DURATION, RED_DURATION + 1, GREEN_DURATION, TOTAL_TIME));
        assertTrue(ex.getMessage().contains("AMBER must be less than or equal to RED"));
    }

    @Test
    void amberNotLessThan33PercentOfGreenShouldFailValidation() {
        int invalidAmber = (int) Math.ceil(GREEN_DURATION * 0.34); // >33% of GREEN
        InvalidConfigurationException ex = assertThrows(InvalidConfigurationException.class, () ->
                ValidationUtil.validateDurations(RED_DURATION, invalidAmber, GREEN_DURATION, TOTAL_TIME));
        assertTrue(ex.getMessage().contains("AMBER must be less than 33% of GREEN"));
    }

    @Test
    void greenNotAtLeast250PercentRedShouldFailValidation() {
        int insufficientGreen = RED_DURATION * 2; // 200% of RED, less than 250%
        InvalidConfigurationException ex = assertThrows(InvalidConfigurationException.class, () ->
                ValidationUtil.validateDurations(RED_DURATION, AMBER_DURATION, insufficientGreen, TOTAL_TIME));
        assertTrue(ex.getMessage().contains("GREEN must be at least 250% of RED"));
    }

    @Test
    void stateTransitionCycleIsCorrect() {
        TrafficLightContext ctx = new TrafficLightContext(RED_DURATION, AMBER_DURATION, GREEN_DURATION, TOTAL_TIME);
        LightColor[] expected = {
                LightColor.RED, LightColor.RED, LightColor.RED, LightColor.RED,
                LightColor.GREEN, LightColor.GREEN, LightColor.GREEN, LightColor.GREEN,
                LightColor.GREEN, LightColor.GREEN, LightColor.GREEN, LightColor.GREEN,
                LightColor.GREEN, LightColor.GREEN, LightColor.AMBER, LightColor.RED,
                LightColor.RED, LightColor.RED, LightColor.RED
        };
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], ctx.tick(), "Tick " + i + " should be " + expected[i]);
        }
    }

    @Test
    void neverEndsOnGreenWhenTotalTimeWouldCutMidGreen() {
        int totalTime = 23; // would cut in middle of GREEN
        TrafficLightContext ctx = new TrafficLightContext(RED_DURATION, AMBER_DURATION, GREEN_DURATION, totalTime);
        LightColor last = null;
        for (int elapsed = 0; elapsed < totalTime; elapsed++) {
            last = ctx.tick();
        }
        assertNotEquals(LightColor.GREEN, last, "Simulation must not end on GREEN");
    }

    @Test
    void greenNeverTransitionsDirectlyToRed() {
        TrafficLightContext ctx = new TrafficLightContext(RED_DURATION, AMBER_DURATION, GREEN_DURATION, TOTAL_TIME);
        // Advance through RED
        for (int i = 0; i < RED_DURATION; i++) {
            ctx.tick();
        }
        // Now in GREEN; advance through GREEN
        for (int i = 0; i < GREEN_DURATION; i++) {
            ctx.tick();
        }
        // Next tick should be AMBER, not RED
        LightColor afterGreen = ctx.tick();
        assertEquals(LightColor.AMBER, afterGreen, "GREEN must transition to AMBER");
    }
}