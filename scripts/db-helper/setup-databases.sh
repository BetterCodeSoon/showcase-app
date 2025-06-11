#!/usr/bin/env bash

#######################################
# How to run this Script (under Linux):
#
# 1) Edit MYSQL_ROOT_PW variable
# 2) Make script executable: chmod +x setup-databases.sh

MYSQL_ROOT_PW="<YourTotallySafePW>"

# Databases for branches and their integration tests:
declare -a DATABASES=("showcase_master"
                      "showcase_integration"
                      "showcase_dev"
                      "showcase_dev_integration")


printf "################################\n"
printf "Starting database setup.. \n"

for DBNAME in "${DATABASES[@]}"
do
  mysql -u root -p${MYSQL_ROOT_PW} -e "create database $DBNAME";
  printf "Database %s was created.\n" "$DBNAME"
done
