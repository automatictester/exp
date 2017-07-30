#!/bin/bash

mkdir ~/docker-volume
(cd compose; docker-compose up --abort-on-container-exit)