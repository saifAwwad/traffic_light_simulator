// Modified by Roo Code AI on 2025-10-02 with model: gpt-oss-120b
package com.example.trafficlightsimulator;

import com.example.trafficlightsimulator.exception.InvalidConfigurationException;
import com.example.trafficlightsimulator.model.state.LightState;
import com.example.trafficlightsimulator.model.state.RedState;
import com.example.trafficlightsimulator.util.ValidationUtil;
import com.example.trafficlightsimulator.service.TrafficLightContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.ApplicationArguments;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;

/**
 * Spring Boot entry point for the Traffic Light Simulator.
 *
 * Accepts CLI arguments:
 *   --red <seconds>
 *   --amber <seconds>
 *   --green <seconds>
 *   --total <seconds>
 *   [--log-level DEBUG|INFO|WARN|ERROR] (optional)
 *
 * Validates constraints and runs the simulation using the Finite State pattern.
 */
@SpringBootApplication
public class TrafficLightSimulatorApplication implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(TrafficLightSimulatorApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TrafficLightSimulatorApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            int redDuration = getIntOption(args, "red");
            int amberDuration = getIntOption(args, "amber");
            int greenDuration = getIntOption(args, "green");
            int totalTime = getIntOption(args, "total");

            // Optional log level
            if (args.containsOption("log-level")) {
                String levelStr = args.getOptionValues("log-level").get(0).toUpperCase();
                setLogLevel(levelStr);
                logger.info("Log level set to {}", levelStr);
            }

            // Validate configuration constraints
            ValidationUtil.validateConfig(redDuration, amberDuration, greenDuration, totalTime);

            // Initialise state machine starting with RED
            LightState initialState = new RedState(redDuration);
            TrafficLightContext context = new TrafficLightContext(
                    initialState,
                    redDuration,
                    amberDuration,
                    greenDuration,
                    totalTime
            );

            logger.info("Starting traffic‑light simulation (total {} seconds)", totalTime);
            while (!context.isFinished()) {
                context.tick();
            }
            logger.info("Simulation completed successfully.");

        } catch (InvalidConfigurationException e) {
            logger.error("Configuration error: {}", e.getMessage());
            System.exit(1);
        } catch (NumberFormatException e) {
            logger.error("Invalid number format for arguments: {}", e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            logger.error("Unexpected error during simulation", e);
            System.exit(1);
        }
    }

    /**
     * Retrieves a required integer option from the CLI arguments.
     *
     * @throws IllegalArgumentException if the option is missing.
     */
    // Modified by Roo Code AI on 2025-10-02 with model: gpt-oss-120b
    private int getIntOption(ApplicationArguments args, String name) {
        // First try Spring Boot option parsing
        if (args.containsOption(name) && !args.getOptionValues(name).isEmpty()) {
            return Integer.parseInt(args.getOptionValues(name).get(0));
        }
        // Fallback: manually parse raw args for formats like "--red 4" or "--red=4"
        String[] source = args.getSourceArgs();
        for (int i = 0; i < source.length; i++) {
            if (source[i].equals("--" + name) && i + 1 < source.length) {
                return Integer.parseInt(source[i + 1]);
            }
            if (source[i].startsWith("--" + name + "=")) {
                return Integer.parseInt(source[i].substring(("--" + name + "=").length()));
            }
        }
        // If still not found, throw the original exception
        throw new IllegalArgumentException("Missing required argument: --" + name);
    }

    /**
     * Sets the root logger level at runtime.
     */
    private void setLogLevel(String levelStr) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        ch.qos.logback.classic.Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
        Level level = Level.toLevel(levelStr, Level.INFO);
        rootLogger.setLevel(level);
    }
}