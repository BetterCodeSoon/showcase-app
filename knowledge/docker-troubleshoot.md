# Docker Troubleshooting

## Table of Contents

- [Problem: Environment file is not used for credentials](#problem-the-env-file-with-database-credentials-is-not-used)

## Problem: The .env file with database credentials is not used

The database container cannot be started properly because:
> > [Warning] Access denied for user 'root'@'localhost' (using password: NO)

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