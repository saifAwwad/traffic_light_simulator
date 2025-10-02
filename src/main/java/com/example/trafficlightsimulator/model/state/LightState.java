// Modified by Roo Code AI on 2025-10-02 with model: gpt-oss-120b
package com.example.trafficlightsimulator.model.state;

import com.example.trafficlightsimulator.model.LightColor;
import com.example.trafficlightsimulator.service.TrafficLightContext;

/**
 * Core interface for all traffic light states.
 * Implementations define the colour, duration, rendering logic and the next state.
 */
public interface LightState {
    /**
     * @return the colour represented by this state.
     */
    LightColor getColor();

    /**
     * @return duration of this state in seconds.
     */
    int getDuration();

    /**
     * Render the current state (e.g., log output) and perform any side‑effects.
     *
     * @param context the traffic‑light context holding runtime data.
     */
    void render(TrafficLightContext context);

    /**
     * Determine the next state based on the current context.
     *
     * @param context the traffic‑ context.
     * @return the next {@link LightState}.
     */
    LightState next(TrafficLightContext context);
}