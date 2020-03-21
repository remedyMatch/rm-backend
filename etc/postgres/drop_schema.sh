#!/bin/bash

DIRECTORY=$(cd `dirname $0` && pwd)

docker stop rmdb
docker rm rmdb

$DIRECTORY/run_pg.sh

