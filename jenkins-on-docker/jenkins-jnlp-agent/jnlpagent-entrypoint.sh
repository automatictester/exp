#!/bin/bash

java -jar slave.jar \
    -jnlpUrl http://jenkins-master:8080/jenkins/computer/jnlp-agent/slave-agent.jnlp \
    -secret "$SECRET" \
    -workDir '/home/jenkins'
