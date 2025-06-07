#!/bin/bash

########################
# Buildscript Setup

#ENV_FILENAME="" # Empty filename results in docker compose looking for unnamed ".env" file
ENV_FILENAME="mariadb-login.env"
ENV_FILE_CMD=""

if [ -n "$ENV_FILENAME" ]; then
  ENV_FILE_CMD="--env-file $ENV_FILENAME"
fi

########################
# Start building..

cd ..

./gradlew clean build

# Clean previous builds
#docker compose down --rmi all

# Build with no cache to execute all steps fresh
#docker compose ${ENV_FILE_CMD} -f compose.yaml build --no-cache

# Build
docker compose ${ENV_FILE_CMD} -f compose.yaml build

# Start containers
docker compose ${ENV_FILE_CMD} -f compose.yaml up