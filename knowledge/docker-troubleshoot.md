# Docker Troubleshooting

## Table of Contents

- [Problem: Environment file is not used for credentials](#problem-the-env-file-with-database-credentials-is-not-used)
- [Problem: Resource still in use](#problem-resource-still-in-use)
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

The following cmds might help.


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