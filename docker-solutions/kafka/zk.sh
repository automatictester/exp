#!/bin/bash

docker service create \
  --name zk \
  --publish 12181:2181 \
  --network kafka-net \
  kafka:latest \
  /kafka/bin/zookeeper-server-start.sh /kafka/config/zookeeper.properties
