#!/bin/bash

DIRECTORY=$(cd `dirname $0` && pwd)

docker run --name rmdb -p 5432:5432 --mount type=bind,source="$DIRECTORY"/pg_data,target=/data -d postgres:rmdb
