#!/bin/bash

(cd images/base; [ ! -f "apache-jmeter-3.2.zip" ] && curl "http://www.mirrorservice.org/sites/ftp.apache.org//jmeter/binaries/apache-jmeter-3.2.zip" -o "apache-jmeter-3.2.zip")
(cd images/base; docker build -t jmeter-base .)
(cd images/server-01; docker build -t jmeter-server-01 .)
(cd images/server-02; docker build -t jmeter-server-02 .)
(cd images/client; docker build -t jmeter-client .)