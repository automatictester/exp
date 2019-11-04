FROM ubuntu:18.04

MAINTAINER X Y <x.y@example.com>

RUN apt-get update && \
    apt-get install -y openjdk-8-jre

ENV kafka_version=2.12-2.3.1
COPY ./kafka_${kafka_version}.tgz ./
RUN tar -xf kafka_${kafka_version}.tgz && \
    mv kafka_${kafka_version} kafka && \
    rm kafka_${kafka_version}.tgz
