// Modified Roo Code AI on 2025-10 gpt-oss-120b
package com.example.trafficlightsimulator.service;

import com.example.trafficlightsimulator.model.LightColor;
import com.example.trafficlightsimulator.model.state.LightState;
import com.example.trafficlightsimulator.model.state.RedState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Holds the runtime state of the traffic‑light simulation.
 * <p>
 * It tracks the current {@link LightState}, the configured durations for each colour,
 * the total simulation time and the elapsed seconds.
 * </p>
 */
public class TrafficLightContext {

    private static final Logger logger = LoggerFactory.getLogger(TrafficLightContext.class);

    private LightState currentState;
    private final int redDuration;
    private final int amberDuration;
    private final int greenDuration;
    private final int totalSimulationTime;

    private int elapsedSeconds = 0;
    // Seconds elapsed within the current state
    private int stateElapsed = 0;

    /** Creates a new simulation with explicit durations. Starts with RED state. */
    public TrafficLightContext(int redDuration,
                               int amberDuration,
                               int greenDuration,
                               int totalSimulationTime) {
        this(new RedState(redDuration), redDuration, amberDuration, greenDuration, totalSimulationTime);
    }

    /** Internal constructor allowing custom initial state (used for testing). */
    public TrafficLightContext(LightState initialState,
                               int redDuration,
                               int amberDuration,
                               int greenDuration,
                               int totalSimulationTime) {
        this.currentState = initialState;
        this.redDuration = redDuration;
        this.amberDuration = amberDuration;
        this.greenDuration = greenDuration;
        this.totalSimulationTime = totalSimulationTime;
    }

    /** @return the colour of the current state */
    public LightColor getCurrentColor() {
        return currentState.getColor();
    }

    /** @return seconds elapsed since simulation start */
    public int getElapsedSeconds() {
        return elapsedSeconds;
    }

    /** @return true if the simulation has reached the allotted total */
    public boolean isFinished() {
        return elapsedSeconds >= totalSimulationTime;
    }

    /** @return RED duration */
    public int getRedDuration() {
        return redDuration;
    }

    /** @return AMBER duration */
    public int getAmberDuration() {
        return amberDuration;
    }

    /** @return GREEN duration */
    public int getGreenDuration() {
        return greenDuration;
    }

    /** @return total simulation time */
    public int getTotalSimulationTime() {
        return totalSimulationTime;
    }

    /** Advances the simulation by one second and returns the colour for that tick. */
    public LightColor tick() {
        if (isFinished()) {
            logger.info("Simulation finished after {} seconds.", elapsedSeconds);
            return null;
        }

        LightColor currentColor = currentState.getColor();
        logger.info("Second {}: {}", elapsedSeconds + 1, currentColor);
        currentState.render(this);
        elapsedSeconds++;
        stateElapsed++;

        // Check if the current state's duration has been exhausted
        if (stateElapsed >= currentState.getDuration()) {
            LightState next = currentState.next(this);

            // Prevent starting a GREEN period that would cause the simulation to end on GREEN
            if (next.getColor() == LightColor.GREEN &&
                elapsedSeconds + next.getDuration() > totalSimulationTime) {
                logger.info("Skipping GREEN to avoid ending on GREEN at second {}.", elapsedSeconds + 1);
                return null;
            }

            logger.debug("Transitioning from {} to {}", currentState.getColor(), next.getColor());
            currentState = next;
            stateElapsed = 0;
        }

        return currentColor;
    }
}