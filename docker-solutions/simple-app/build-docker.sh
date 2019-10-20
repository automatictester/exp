#!/usr/bin/env bash

./build-jar.sh
docker build -t simple-app .
