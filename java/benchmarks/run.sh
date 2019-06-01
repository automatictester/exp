#!/usr/bin/env bash

mvn clean package
java \
     -XX:+AlwaysPreTouch \
     -Xms2g \
     -Xmx2g \
     -jar target/benchmarks.jar
