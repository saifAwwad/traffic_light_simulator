// Modified by Roo Code AI on 2025-10-02 with model: gpt-oss-120b
package com.example.trafficlightsimulator.exception;

/**
 * Thrown when the provided simulation configuration violates business constraints.
 */
public class InvalidConfigurationException extends RuntimeException {

    public InvalidConfigurationException(String message) {
 super(message);
    }
}