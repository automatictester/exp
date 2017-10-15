#!/bin/bash

# build base image before running docker-compose
(cd images/base; [ ! -f "apache-jmeter-3.3.zip" ] && curl "http://www.mirrorservice.org/sites/ftp.apache.org//jmeter/binaries/apache-jmeter-3.3.zip" -o "apache-jmeter-3.3.zip")
(cd images/base; docker build -t jmeter-base .)

docker-compose up --abort-on-container-exit
