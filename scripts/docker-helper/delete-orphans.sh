#!/bin/bash

# Maintenance script to free up space and remove orphans

echo "Deleting orphaned images"
docker image prune

echo "Delete orphaned containers"
docker container prune

echo "Delete orphaned volumes"
docker volume prune

echo "Delete orphaned networks"
docker network prune

echo "Switching to compose.yaml dir"
cd ..
cd ..
echo "Delete containers by previous configuration"
docker compose down --remove-orphans