#!/bin/bash

cd ..
./gradlew clean build

# Clean previous builds
#docker compose down --rmi all

# Rebuild with no cash to execute all steps fresh
#docker compose -f compose.yaml build --no-cache

# Rebuild with corrected setup
docker compose -f compose.yaml build
docker compose -f compose.yaml up