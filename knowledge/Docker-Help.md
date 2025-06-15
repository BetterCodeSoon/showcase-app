# 🐳 Docker Help and Troubleshooting

This content refers to:

Docker version 28.2.2, build e6534b4  
Docker Compose version v2.36.2

Under Ubuntu 24.04.2 LTS.

## Table of Contents

- [Problem: Environment file is not used for credentials](#problem-the-env-file-with-database-credentials-is-not-used)
- [Problem: Resource still in use](#problem-resource-still-in-use)
- [How-to: Optimizing container size](#how-to-optimizing-container-size)
- [How-to: Pin specific image versions](#how-to-pin-specific-image-versions)
- [Useful Commands](#useful-commands)


## Problem: The .env file with database credentials is not used

The database container cannot be started properly because:
> [Warning] Access denied for user 'root'@'localhost' (using password: NO)

This is a well-documented and frustrating issue with MariaDB (and MySQL) Docker images:
if the data directory (/var/lib/mysql or your bind mount) is not empty, the MariaDB container
will ignore the environment variables for root password and database/user creation.
This means you can have a correct .env file, but if the data directory already has content
(even partial or from a failed init), the entrypoint script will skip initialization and ignore your env vars.

`sudo rm -rf /mariadb-data/*`

### If you are not using a volume or bind mount, MariaDB's data is stored inside the container. To fully reset:

`docker compose down`

### If you want to also remove any images (optional, for a truly clean slate):

`docker compose down --rmi all`

### If you ever attach a named volume or bind mount for persistence, add --volumes to remove those as well:

`docker compose down --volumes`

### Delete container

See all containers and find out id  
`docker ps -a  `  
`docker rm <container_id_or_name>`
---

## Problem: Resource still in use

Sometimes, containers can get stuck in a "removal in progress" or "dead" state, 
which prevents image deletion even though they don't show up in "docker ps -a".

See the cleanup script:  
[../scripts/docker-helper/delete-orphanes.sh](../scripts/docker-helper/delete-orphans.sh)

Otherwise, the following cmds might help for specific cases

List dangling (unused) volumes:  
`docker volume ls -f dangling=true`

Remove all unused volumes:  
`docker volume prune`

Remove all stopped containers:  
`docker container prune`

Remove all unused networks:  
`docker network prune`

Remove dangling image:  
`docker image prune`

Remove all unused images:  
`docker image prune -a`

**WARNING** this will delete all images  
and perform full system cleanup (last resort!):  
`docker system prune -a`
---

## How-to: Optimizing container size

Use multistage Dockerfiles.
See: https://docs.docker.com/build/building/multi-stage/

Jar file should be run with an JRE instead of JDK.

Here are some options:

| Image Type                        | Typical Size   | Notes                                      |
|------------------------------------|---------------|--------------------------------------------|
| openjdk:21-jdk-slim               | ~300MB+       | Full JDK, not minimal                      |
| eclipse-temurin:21-jre            | ~100-200MB    | JRE only, smaller than JDK                 |
| eclipse-temurin:21-jre-alpine     | ~50-100MB     | Smallest official JRE, if available        |
| **Custom JRE (JLink)**            | 50MB or less  | Smallest possible, only needed modules     |
| BellSoft Alpaquita, Corretto Alpine| ~40-90MB      | Vendor-optimized, very small               |

See slim spring boot container:  
https://github.com/mgrecuccio/slim-spring-boot
---

## How-to: Pin specific image versions

Use the script to get the digest hash under:  
[/scripts/docker-helper/image-digest.sh](../scripts/docker-helper/image-digest.sh)

Pining works in compose.yaml and Dockerfile.

In the Dockerfile:
> FROM openjdk@sha256:7072053847a8a05d7f3a14ebc778a90b38c50ce7e8f199382128a53385160688 as builder

instead of just writing:  
> FROM openjdk:21-jdk-slim

Same thing in the compose.yaml:  
>image: mariadb@sha256:1d18f91deb21136d1881705720071d1b474a9904ecca827058bf1c0fc64d3118

instead of:  
>image:mariadb:11.4.7


Further reading:  
https://docs.docker.com/build/building/best-practices/#pin-base-image-versions
---

## Useful Commands

Access container  
`docker exec -it <container_id> /bin/bash` 

Stopping all containers  
`docker stop $(docker ps -a -q)` 

Removing all containers  
`docker rm $(docker ps -a -q)`  

Starts all services from compose  
`docker compose up -d`

Show Config  
`docker compose config`

Delete Orphans  
`docker compose down --remove-orphans`  

Build with no cache to execute all steps fresh  
`docker compose build --no-cache`

Prune old networks that cause issues  
`docker compose down -v`  
`docker network prune`