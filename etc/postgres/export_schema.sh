#!/bin/bash
#
# Call:
#   export_shema.sh [<filename_without_path>]
#
# Exports the entire db and outputs the result under etc/postgres/pg_data.
#
# Parameters
#   <filename_without_path> is optional and specifies the name of the export file.
#       Do not use path qualifier.


DIRECTORY=$(cd `dirname $0` && pwd)

if [ -z "$1" ]
then
    EXPORT_FILE=/data/rmdb-pg_dump.pg
else
    EXPORT_FILE=/data/$1
fi

docker exec frdb sh -c "pg_dump -Fc --no-owner -c -C -U rmbe rmdb >$EXPORT_FILE"

