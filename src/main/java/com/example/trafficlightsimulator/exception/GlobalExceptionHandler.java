// Modified by Roo Code AI on 2025-10-02 with model: gpt-oss-120b
package com.example.trafficlightsimulator.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Centralized handler for uncaught runtime exceptions in the CLI application.
 * Ensures a clean message and non‑zero exit code.
 */

public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles any {@link RuntimeException} that propagates out of the application.
     *
     * @param e the exception to handle
     */
    public static void handle(RuntimeException e) {
        if (e instanceof InvalidConfigurationException) {
            logger.error("Configuration error: {}", e.getMessage());
        } else {
            logger.error("Unexpected error: {}", e.getMessage(), e);
        }
        // Ensure the application exits with a failure status
        System.exit(1);
    }
}