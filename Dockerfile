#################################
# Build stage

# Pin openjdk:21-jdk-slim version by using specific digest
FROM openjdk@sha256:7072053847a8a05d7f3a14ebc778a90b38c50ce7e8f199382128a53385160688 AS builder

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

# Image eclipse-temurin:21-jre-alpine (lightweight JRE ~50-100mb)
FROM eclipse-temurin@sha256:8728e354e012e18310faa7f364d00185277dec741f4f6d593af6c61fc0eb15fd

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