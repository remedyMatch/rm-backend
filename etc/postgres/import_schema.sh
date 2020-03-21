#!/bin/bash

DIRECTORY=$(cd `dirname $0` && pwd)

if [ -z $1 ]; then IMPORT_FILE=/data/factdb-pg_dump.pg ; else IMPORT_FILE="/data/$1"; fi

echo Importing dump from $IMPORT_FILE

docker exec frdb sh -c "pg_restore --verbose --clean --no-acl --no-owner -h localhost -U frmberdb -d rmdb $IMPORT_FILE"


