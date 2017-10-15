FROM jmeter-base

WORKDIR /jmeter/bin

COPY test-plan.jmx .

RUN sed -i 's/.*remote_hosts=127.*/remote_hosts=jmeter-server-01,jmeter-server-02/' jmeter.properties
RUN mkdir results

CMD ./jmeter -n -t test-plan.jmx -l results/results.jtl -j results/execution.log -R jmeter-server-01,jmeter-server-02