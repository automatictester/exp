FROM jmeter-base

WORKDIR /jmeter/bin

ADD test-plan.jmx .

RUN sed -i 's/.*remote_hosts=127.*/remote_hosts=172.25.0.11,172.25.0.12/' jmeter.properties
RUN mkdir results

CMD ./jmeter -n -t test-plan.jmx -l results/results.jtl -j results/execution.log -R 172.25.0.11,172.25.0.12