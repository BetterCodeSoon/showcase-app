#################################
# Build stage
FROM openjdk:21-jdk-slim AS builder

# Define a variable for the working directory
ENV APP_PATH=/showcase-app

# Set the working directory in the container
WORKDIR $APP_PATH

# Copy Gradle wrapper files
COPY gradlew gradlew
COPY gradle gradle

# Copy Gradle configuration files
COPY build.gradle settings.gradle ./

# Copy application source code
COPY src src

# Build the application using Gradle
RUN ./gradlew bootJar


#################################
# Runtime Stage
FROM openjdk:21-jdk-slim

# Define a variable for the working directory
ENV APP_PATH=/showcase-app

# Set the working directory in the container
WORKDIR $APP_PATH

# Copy the JAR file from the build stage
COPY --from=builder $APP_PATH/build/libs/showcase-backend.jar .

# Expose the port the app runs on
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/showcase-app/showcase-backend.jar"]