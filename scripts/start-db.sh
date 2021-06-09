#!/bin/bash
set -euo pipefail

DB_CONTAINER_NAME=cardgame-database

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"

CONTAINER_RUNTIME=docker

DATABASE_DB=spring_cardgame
DATABASE_USER=postgres
DATABASE_PASSWORD=postgres
DATABASE_PORT=5431

if ! $CONTAINER_RUNTIME inspect $DB_CONTAINER_NAME >/dev/null 2>&1; then
    $CONTAINER_RUNTIME run --restart=always -d --name $DB_CONTAINER_NAME \
        -e POSTGRES_DB=$DATABASE_DB \
        -e POSTGRES_USER=$DATABASE_USER \
        -e POSTGRES_PASSWORD=$DATABASE_PASSWORD \
        -p $DATABASE_PORT:5432 \
        postgres:13 \
        postgres -c log_statement=all
else
    echo "Database already exists"
    $CONTAINER_RUNTIME ps -a | grep $DB_CONTAINER_NAME
fi

# $DIR/wait-db.sh
# sleep 4


# $CONTAINER_RUNTIME exec $DB_CONTAINER_NAME psql -U $DATABASE_USER -c "CREATE DATABASE $TEST_DB;"
