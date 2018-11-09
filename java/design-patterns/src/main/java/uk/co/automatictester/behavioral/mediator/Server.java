package uk.co.automatictester.behavioral.mediator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.*;

public class Server {

    private static final Logger log = LoggerFactory.getLogger(Server.class);

    private Set<Client> clients = new ConcurrentSkipListSet<>();
    private Queue<Message> messages = new ConcurrentLinkedQueue<>();
    private ScheduledExecutorService service;

    public Server() {
        setup();
    }

    public void register(Client client) {
        log.info("Registered client '{}'", client.getName());
        clients.add(client);
    }

    public void deregister(Client client) {
        log.info("Deregistered client '{}'", client.getName());
        clients.remove(client);
    }

    public void send(Message message) {
        log.info("Server received message from '{}' to '{}'",
                message.sender().getName(),
                message.recipient()
        );
        messages.add(message);
    }

    private void setup() {
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(dispatch, 0, 100, TimeUnit.MILLISECONDS);
    }

    public void shutdown() {
        if (service != null) {
            service.shutdown();
        }
    }

    private Runnable dispatch = () -> {
        log.info("Messages to process: {}", messages.size());
        int unprocessedMessages = 0;
        for (Message message : messages) {

            String recipientName = message.recipient();
            Optional<Client> client = getClient(recipientName);

            if (client.isPresent()) {
                log.info("Dispatching message from '{}' to '{}'",
                        message.sender().getName(),
                        message.recipient()
                );
                client.get().receive(message);
                messages.remove(message);
            } else {
                unprocessedMessages++;
                log.info("Unable to dispatch message from '{}' to '{}'",
                        message.sender().getName(),
                        message.recipient()
                );
            }
        }
        if (unprocessedMessages > 0) log.info("Unprocessed messages: {}", unprocessedMessages);
    };

    private Optional<Client> getClient(String name) {
        return clients.stream()
                .filter(c -> c.getName().equals(name))
                .findFirst();
    }
}
