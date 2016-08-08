#!/bin/bash -xe

IMAGE_NAME=db-test-btl
PREVIOUS=$(docker ps -aq --filter=name=${IMAGE_NAME})
if [ "$PREVIOUS" != "" ]; then
    docker rm -vf ${PREVIOUS}
fi

if [ "$1" != "private" ]; then
    EXPOSE_PORT="-p 5432:5432"
fi

docker run -d --name ${IMAGE_NAME} ${EXPOSE_PORT}  btl/db:test
