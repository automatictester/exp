package uk.co.automatictester.behavioral.mediator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client implements Comparable<Client> {

    private static final Logger log = LoggerFactory.getLogger(Client.class);

    private String name;
    private Server server;

    public Client(String name) {
        this.name = name;
    }

    public void register(Server server) {
        this.server = server;
        server.register(this);
    }

    public void deregister() {
        if (server != null) {
            this.server.deregister(this);
            this.server = null;
        }
    }

    public void send(String recipient, String text) {
        log.info("'{}' Sending message '{}' to '{}'", name, text, recipient);
        Message message = new Message(this, recipient, text);
        server.send(message);
    }

    public void receive(Message message) {
        String sender = message.sender().getName();
        String content = message.text();
        log.info("'{}' Received message '{}' from '{}'", name, content, sender);
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Client other) {
        return name.compareTo(other.name);
    }
}
