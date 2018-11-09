package uk.co.automatictester.behavioral.mediator;

public class Message {
    private Client sender;
    private String recipient;
    private String text;

    public Message(Client sender, String recipient, String text) {
        this.sender = sender;
        this.recipient = recipient;
        this.text = text;
    }

    public Client sender() {
        return sender;
    }

    public String recipient() {
        return recipient;
    }

    public String text() {
        return text;
    }
}
