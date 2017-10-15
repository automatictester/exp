FROM jmeter-base

WORKDIR /jmeter/bin

RUN echo "server.rmi.localport=54321" >> jmeter.properties
RUN sed -i 's/.*RMI_HOST_DEF=.*/RMI_HOST_DEF=-Djava.rmi.server.hostname=jmeter-server-02/' jmeter-server

CMD ./jmeter-server