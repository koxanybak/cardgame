#!/bin/bash
set -euo pipefail

echo "Checking if database is up"
while true; do
    if nc -z $DATABASE_HOST 5432; then
        break
    else
        echo "Waiting for database"
        sleep 2
    fi
done
