# Traffic Light Simulator Prompt (Paraphrased)

**Role**: Senior Java engineer creating a‑grade, modular Spring Boot CLI app that simulates a traffic light using the Finite State pattern.  
**Quality**: robust SLF4J/Logback logging, strict validation, defensive coding (no NPEs), full unit‑test coverage, and a tidy project structure.

### Problem
Build a CLI that accepts durations for RED, AMBER, GREEN lights and a total simulation time (seconds). The app prints the current light each second until the total time is reached, obeying the constraints below.

### Constraints
- Start with RED.  
- AMBER ≤ RED.  
- AMBER < 33 % of GREEN.  
- GREEN ≥ 250 % of RED.  
- No direct GREEN → RED transition (must go GREEN → AMBER → RED).  
- Simulation must never end on GREEN; omit the final GREEN tick if it would exceed the total time.  
- Total elapsed time must not exceed the supplied budget.

### Architecture & Patterns
- **State**: each light (RED, GREEN, AMBER, …) implements a `LightState` interface; states are added at runtime.  
- **Context**: `TrafficLightContext` holds the current state and the configured durations.  
- **Transition**: rendering logic prints the state and advances to the next state while respecting business constraints, which can be changed at runtime.  
- Keep the design simple (KISS).

### Input & Configuration
- CLI options: `--red`, `--amber`, `--green`, `--total`, optional `--log-level` (DEBUG/INFO/WARN/ERROR).  
- Validate all inputs (type, range, presence) and provide clear error messages; the program must not crash on invalid data.

### Logging & Observability
- Use SLF4J with Logback.  
- **INFO**: lifecycle start/stop and state transitions.  
- **DEBUG**: detailed internal steps.  
- **WARN/ERROR**: validation failures or runtime problems.

### Code Quality
- Follow Java standards, run a linter/formatter.  
- Provide `.gitignore` for Java/Maven/IDE files.  
- Global exception handler with descriptive messages and error codes.  
- Project layout follows typical Spring Boot structure: `model`, `service`, `util`, `config`, `exception`.

### Tests
- Unit tests for constraint validation, state cycle order, “never end on GREEN”, and CLI parsing (including missing/invalid arguments).

### Build & Run
- Package: `mvn -q -DskipTests package`.  
- Execute: `java -jar target/traffic-light-simulator.jar --red 4 --amber 1 --green 10 --total 22 --log-level DEBUG`.  
- `README.md` must contain description, architecture overview, constraints table, build/run instructions, example output, and the exact prompt used.

### Future Enhancements (not implemented)
- Additional light colors, dynamic configuration.
- Micro service

### Non‑Functional Requirements
- No NPEs; all optionals validated.  
- Deterministic core logic (no `Thread.sleep` in state logic).  
- Clear exit codes on error.  
- Clean, readable code with Javadoc on public APIs.  
- Dockerized solution (Dockerfile provided).