#!/bin/bash

DIRECTORY=$(cd `dirname $0` && pwd)

(cd $DIRECTORY; docker build . -t postgres:rmdb)
