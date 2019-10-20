#!/usr/bin/env bash

./build-docker.sh
docker run -p 8080:8080 simple-app
