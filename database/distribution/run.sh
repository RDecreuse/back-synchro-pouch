#!/bin/bash -xe

IMAGE_NAME=db-btl
PREVIOUS=$(docker ps -aq --filter=name=db-btl)
if [ "$PREVIOUS" != "" ]; then
    docker rm -f ${PREVIOUS}
fi

docker run -d --name ${IMAGE_NAME} -p 5432:5432 btl/db:latest
