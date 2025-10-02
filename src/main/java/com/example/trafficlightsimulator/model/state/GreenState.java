// Modified by Roo Code AI on 2025-10-02 with model: gpt-oss-120b
package com.example.trafficlightsimulator.model.state;

import com.example.trafficlightsimulator.model.LightColor;
import com.example.trafficlightsimulator.service.TrafficLightContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Green light state.
 * Duration is supplied at construction configuration.
 * Transitions to {@link AmberState} after its duration expires.
 */
public class GreenState implements LightState {

    private static final Logger logger = LoggerFactory.getLogger(GreenState.class);
    private final int duration;

    public GreenState(int duration) {
        this.duration = duration;
    }


    public LightColor getColor() {
        return LightColor.GREEN;
    }


    public int getDuration() {
        return duration;
    }

    public void render(TrafficLightContext context) {
        logger.info("GREEN");
    }

    public LightState next(TrafficLightContext context) {
        // Transition to AMBER after GREEN
        return new AmberState(context.getAmberDuration());
    }
}