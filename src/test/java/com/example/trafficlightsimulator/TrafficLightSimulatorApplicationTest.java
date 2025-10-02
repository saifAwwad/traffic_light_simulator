package com.example.trafficlightsimulator;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.ApplicationArguments;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class TrafficLightSimulatorApplicationTest {

    /**
     * Helper to invoke the private {@code getIntOption} method via reflection.
     */
    // Modified by Roo Code AI on 2025-10-02 with model: gpt-oss-120b
    private int invokeGetIntOption(ApplicationArguments args, String name) throws Exception {
        Class<?> clazz = TrafficLightSimulatorApplication.class;
        Method method = clazz.getDeclaredMethod("getIntOption", ApplicationArguments.class, String.class);
        method.setAccessible(true);
        try {
            // Invoke on a new instance of the application class
            return (int) method.invoke(new TrafficLightSimulatorApplication(), args, name);
        } catch (InvocationTargetException e) {
            // Unwrap the underlying exception thrown by getIntOption
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            } else if (cause instanceof Exception) {
                throw (Exception) cause;
            } else {
                throw e;
            }
        }
    }

    @Test
    void getIntOption_parsesSeparateOptionAndValue() throws Exception {
        ApplicationArguments args = new DefaultApplicationArguments(new String[]{
                "--red", "5",
                "--amber", "2",
                "--green", "3",
                "--total", "10"
        });

        assertEquals(5, invokeGetIntOption(args, "red"));
        assertEquals(2, invokeGetIntOption(args, "amber"));
        assertEquals(3, invokeGetIntOption(args, "green"));
        assertEquals(10, invokeGetIntOption(args, "total"));
    }

    @Test
    void getIntOption_parsesOptionWithEqualsSyntax() throws Exception {
        ApplicationArguments args = new DefaultApplicationArguments(new String[]{
                "--red=5",
                "--amber=2",
                "--green=3",
                "--total=10"
        });

        assertEquals(5, invokeGetIntOption(args, "red"));
        assertEquals(2, invokeGetIntOption(args, "amber"));
        assertEquals(3, invokeGetIntOption(args, "green"));
        assertEquals(10, invokeGetIntOption(args, "total"));
    }

    @Test
    void getIntOption_missingArgumentThrowsException() {
        ApplicationArguments args = new DefaultApplicationArguments(new String[]{
                "--red", "5"
        });

        Executable call = () -> invokeGetIntOption(args, "amber");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, call);
        assertTrue(exception.getMessage().contains("Missing required argument: --amber"));
    }
}