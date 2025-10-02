// Modified by Roo Code AI on 2025-10-02 with model: gpt-oss-120b
package com.example.trafficlightsimulator.model.state;

import com.example.trafficlightsimulator.model.LightColor;
import com.example.trafficlightsimulator.service.TrafficLightContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Red light state.
 * Duration is supplied at construction based on configuration.
 */
public class RedState implements LightState {

    private static final Logger logger = LoggerFactory.getLogger(RedState.class);
    private final int duration;

    public RedState(int duration) {
        this.duration = duration;
    }

    public LightColor getColor() {
        return LightColor.RED;
    }

    public int getDuration() {
        return duration;
    }

    public void render(TrafficLightContext context) {
        logger.info("RED");
    }


    public LightState next(TrafficLightContext context) {
        // Transition to GREEN after RED
        return new GreenState(context.getGreenDuration());
    }
}