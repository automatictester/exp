#!/bin/bash

docker service create \
    --name k \
    --publish 19092:19092 \
    --network kafka-net \
    kafka:latest \
    /kafka/bin/kafka-server-start.sh /kafka/config/server.properties \
     --override listeners=PLAINTEXT://0.0.0.0:19092 \
     --override inter.broker.listener.name=PLAINTEXT \
     --override advertised.listeners=PLAINTEXT://localhost:19092 \
     --override zookeeper.connect=zk:2181 \
