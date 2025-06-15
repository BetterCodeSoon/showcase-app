# 🐬 MariaDB Help and Troubleshooting

This is content refers to:  

Mariadb:11.4.7  
Docker image: `mariadb@sha256:1d18f91deb21136d1881705720071d1b474a9904ecca827058bf1c0fc64d3118`

Under Ubuntu 24.04.2 LTS.

## Table of Contents

- [1. How-To: Install MariaDB](#1-how-to-install-mariadb)
- [2. How-To: Setup custom data directory](#2-how-to-setup-custom-data-directory)
- [3. How-To: Define default character set and collation](#3-how-to-define-default-character-set-and-collation)
- [4. How-To: Change MariaDB Port](#4-how-to-change-mariadb-port)
- [Misc: Useful commands](#misc-useful-commands)

## 1. How-To: Install MariaDB

...

---


## 2. How-To: Setup custom data directory

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

---

## 3. How-To: Define default character set and collation

UTF-8 ensures consistent sorting and comparison behaviour for strings.

Edit Config:  
`sudo nano /etc/mysql/mariadb.cnf`

Add:  
[mysqld]  
character-set-server = utf8mb4  
collation-server = utf8mb4_unicode_ci  
init-connect='SET NAMES utf8mb4'

Restart MariaDB afterwards.  
`sudo systemctl restart mariadb`

---

## 4. How-To: Change MariaDB Port

### 4.1. Open Config File  
`sudo nano /etc/mysql/mariadb.conf.d/50-server.cnf`

### 4.2. Look for [mysql] section or add it on top of the file

MariaDB standart port is 3306. Choose something else. 
>[mysqld]  
port = 7307

### 4.3. Restart MariaDB Service

`sudo systemctl restart mariadb`

### 4.4. Verify the new port is working

`sudo ss -tulnp | grep mysql`

---

## Misc: Useful commands

### Start MariaDB

`sudo systemctl start mariadb`

### Stop MariaDB

`sudo systemctl stop mariadb`

### Restart MariaDB
`sudo systemctl restart mariadb`