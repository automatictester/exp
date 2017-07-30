FROM ubuntu:17.04
FROM openjdk:8u141-jdk

RUN apt-get update
RUN apt-get install -y net-tools vim

WORKDIR /jmeter

ADD apache-jmeter-3.2.zip .

RUN unzip apache-jmeter-3.2.zip
RUN mv apache-jmeter-3.2/* .
RUN rmdir apache-jmeter-3.2
RUN rm apache-jmeter-3.2.zip
RUN echo "client.rmi.localport=1234" >> bin/jmeter.properties
