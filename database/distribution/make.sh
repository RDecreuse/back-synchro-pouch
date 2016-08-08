#!/bin/bash

BASE_DIR=$(cd $(dirname $0); pwd)

rm -rf ${BASE_DIR}/docker-entrypoint-initdb.d/*

cp -rp ${BASE_DIR}/../incremental/* ${BASE_DIR}/docker-entrypoint-initdb.d/

docker build -t btl/db:latest ${BASE_DIR}

