#!/bin/bash

# This script gets the digest hash for an image that can be used to pin the version
# see https://docs.docker.com/build/building/best-practices/#pin-base-image-versions

if [ -z "$1" ]; then
  echo "Usage: $0 <image-name>"
  exit 1
fi

IMAGE="$1"

echo "Pulling image: $IMAGE"
docker pull "$IMAGE"

echo "Inspecting image: $IMAGE"
docker inspect --format='{{index .RepoDigests 0}}' "$IMAGE"

echo "Cleanup by deleting image: $IMAGE"
docker rmi "$IMAGE"