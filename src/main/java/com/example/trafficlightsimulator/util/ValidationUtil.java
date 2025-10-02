// Modified by Roo Code AI on 2025-10-02 with model: gpt-oss-120b
package com.example.trafficlightsimulator.util;

import com.example.trafficlightsimulator.exception.InvalidConfigurationException;

/**
 * Utility class for validating command‑line arguments and business constraints.
 * All validation failures throw {@link InvalidConfigurationException} with a clear message.
 */
public final class ValidationUtil {

    private ValidationUtil() {
        // Utility class – prevent instantiation
 }

    /**
     * Validates the simulation configuration.
     *
     * @param redDuration   duration of RED in seconds (must be > 0)
     * @param amberDuration duration of AMBER in seconds (must be > 0)
     * @param greenDuration duration of GREEN in seconds (must be > 0)
     * @param totalTime     total simulation time in seconds (must be > 0)
     * @throws InvalidConfigurationException if any constraint is violated
     */
    public static void validateConfig(int redDuration,
                                      int amberDuration,
                                      int greenDuration,
                                      int totalTime) {

        if (redDuration <= 0) {
            throw new InvalidConfigurationException("RED duration must be a positive integer.");
        }
        if (amberDuration <= 0) {
            throw new InvalidConfigurationException("AMBER duration must be a positive integer.");
        }
        if (greenDuration <= 0) {
            throw new InvalidConfigurationException("GREEN duration must be a positive integer.");
        }
        if (totalTime <= 0) {
            throw new InvalidConfigurationException("Total simulation time must be a positive integer.");
        }

        // Constraint: AMBER ≤ RED
        if (amberDuration > redDuration) {
            throw new InvalidConfigurationException(
                "AMBER must be less than or equal to RED");
        }

        // Constraint: AMBER < 33% of GREEN (i.e., amber * 100 < 33 * green)
        if (amberDuration * 100 >= 33 * greenDuration) {
            throw new InvalidConfigurationException(
                "AMBER must be less than 33% of GREEN");
        }

        // Constraint: GREEN ≥ 250% of RED (i.e., green * 10 >= 25 * red)
        if (greenDuration * 10 < 25 * redDuration) {
            throw new InvalidConfigurationException(
                "GREEN must be at least 250% of RED");
        }

        // Additional safeguard: total time must be at least the sum of one full cycle
        int oneCycle = redDuration + amberDuration + greenDuration;
        if (totalTime < oneCycle) {
            throw new InvalidConfigurationException(
                String.format("Total simulation time (%d) is less than one full cycle (%d seconds).", totalTime, oneCycle));
        }
    }

    /**
     * Alias method to maintain compatibility with existing test suite.
     *
     * @param redDuration   duration of RED in seconds
     * @param amberDuration duration of AMBER in seconds
     * @param greenDuration duration of GREEN in seconds
     * @param totalTime     total simulation time in seconds
     */
    public static void validateDurations(int redDuration,
                                         int amberDuration,
                                         int greenDuration,
                                         int totalTime) {
        // Delegate to the primary validation method
        validateConfig(redDuration, amberDuration, greenDuration, totalTime);
    }
}