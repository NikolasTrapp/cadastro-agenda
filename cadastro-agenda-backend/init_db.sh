#!/usr/bin/env sh

set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE DATABASE "$DB_DATABASENAME" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.utf8';    
EOSQL