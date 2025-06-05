# Database Setup

## Table of Contents

- [1. Install MariaDB](#1-install-mariadb)
- [2. Setup custom data directory](#2-setup-custom-data-directory)
- [3. Define default character set and collation](#3-define-default-character-set-and-collation)
- [4. Useful commands](#4-useful-commands)

(... for Ubuntu 24.10)

## 1. Install MariaDB

...

## 2. Setup custom data directory

Why?
An easy accessible, **absolute path** custom directory
can be used for persistency between containers with a docker volume.

### 2.1. Create dir

`sudo mkdir mariadb-data`

### 2.2. Give permissions and ownership for mysql user

`sudo chown -R 999:999 /mariadb-data`  
`sudo chmod 700 /mariadb-data`

### 2.3. Edit MariaDB config

Stop MariaDB:  
`sudo systemctl stop mariadb`

Then open config:  
`sudo nano /etc/mysql/mariadb.cnf`

Add or edit in:  
[mysqld]  
datadir = /mariadb-data

Save the file and exit.

### 2.4. Copy existing databases to the new dir

Copy respective files with permissions:  
`sudo cp -a /var/lib/mysql/. /mariadb-data/`

### 2.5. Restart MariaDB

`sudo systemctl start mariadb`

### 2.6. Check if the new dir works

Log into MariaDB:  
`mysql -u root -p`  
or  
`'/usr/bin/mariadb -u root -p'`

Check databases:  
`SHOW DATABASES;`

## 3. Define default character set and collation

UTF-8 ensures consistent sorting and comparison behaviour for strings.

Edit Config:  
`sudo nano /etc/mysql/mariadb.cnf`

Add:  
[mysqld]  
character-set-server = utf8mb4  
collation-server = utf8mb4_unicode_ci  
init-connect='SET NAMES utf8mb4'

Restart MariaDB afterwards.

## 4. Useful commands

### 4.2 Start MariaDB

`sudo systemctl start mariadb`

### 4.3 Stop MariaDB

`sudo systemctl stop mariadb`
