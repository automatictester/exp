#!/usr/bin/env bash

mvn clean package
java -jar target/simple-app-1.0-shaded.jar
