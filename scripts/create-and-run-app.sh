#!/bin/bash
# IDEA warning suppression:
# shellcheck disable=SC2086

# Cmd argument to activate full clean gradle build
CLEAN_BUILD_PARAM="clean"

if [ -z "$1" ]; then
  echo "Optional usage to activate clean build: $0 $CLEAN_BUILD_PARAM"
  echo "Running script with normal build option.."
fi

########################
# Buildscript Setup

# By default Docker will use the folder name above the compose.yaml as container name.
# Edit the variable to change to a different name.
#CONTAINERNAME="showcase-app"
CONTAINERNAME=""

# By default Docker compose will look for unnamed an ".env" file (same dir as compose.yaml).
# Edit this variable if you want to switch to a named file.
# (WARNING: NOT recommended as this can lead to unexpected behaviour!)
#ENV_FILENAME="mariadb-login.env"
ENV_FILENAME=""

########################
# Setting up command line parameters for docker compose
CONTAINERNAME_PARAM=""
if [ -n "$CONTAINERNAME" ]; then
  CONTAINERNAME_PARAM="-p $CONTAINERNAME"
fi

ENV_FILE_PARAM=""
if [ -n "$ENV_FILENAME" ]; then
  ENV_FILE_PARAM="--env-file $ENV_FILENAME"
fi

########################
# Start building..

cd ..

if [[ "$1" == "$CLEAN_BUILD_PARAM" ]]; then
  echo "Running: ./gradlew clean build (full clean build)"
  ./gradlew clean build
else
  echo "Running: ./gradlew build (incremental build)"
  ./gradlew build
fi

# Clean previous builds
#docker compose down --rmi all

# Remove orphans
#docker compose down --remove-orphans

# Build with no cache to execute all steps fresh
#docker compose ${ENV_FILE_PARAM} ${CONTAINERNAME_PARAM} -f compose.yaml build --no-cache

# Build
docker compose ${ENV_FILE_PARAM} ${CONTAINERNAME_PARAM} -f compose.yaml build

# Start containers
docker compose ${ENV_FILE_PARAM} ${CONTAINERNAME_PARAM} -f compose.yaml up