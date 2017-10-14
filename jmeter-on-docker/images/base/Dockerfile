FROM openjdk:8u141-jdk

RUN apt-get update
RUN apt-get install -y net-tools vim

WORKDIR /jmeter

COPY apache-jmeter-3.3.zip .

RUN unzip apache-jmeter-3.3.zip
RUN mv apache-jmeter-3.3/* .
RUN rmdir apache-jmeter-3.3
RUN rm apache-jmeter-3.3.zip
RUN echo "client.rmi.localport=1234" >> bin/jmeter.properties
