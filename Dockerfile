# Use a lightweight JDK image
FROM eclipse-temurin:17-jre-alpine

# Set working directory
WORKDIR /app

# Copy the built jar (assumes Maven package has been run)
COPY target/traffic-light-simulator-0.0.1-SNAPSHOT.jar app.jar

# Expose no ports (CLI app)

# Default command
ENTRYPOINT ["java","-jar","app.jar"]