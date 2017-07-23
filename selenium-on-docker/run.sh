#!/usr/bin/env bash

# Run Selenium tests on Docker Compose-based Selenium Grid
mvn clean verify

# Capture exit code
OUT=$?

# If it failed, stop Docker Compose and notify CI server using exit code
if [ ${OUT} -eq 0 ];then
    exit 0
else
    mvn antrun:run@compose-stop
    exit 1
fi
