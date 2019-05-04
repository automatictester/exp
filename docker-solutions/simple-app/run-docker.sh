#!/usr/bin/env bash

mvn clean package
docker build -t simple-app .
docker run -p 8080:8080 simple-app
