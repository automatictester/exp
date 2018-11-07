package uk.co.automatictester.behavioral.chainofresponsibility.request;

import uk.co.automatictester.behavioral.chainofresponsibility.handler.Request;

import static uk.co.automatictester.behavioral.chainofresponsibility.handler.RequestType.INSERT;

public class InsertHandler implements Handler {

    private Handler next;

    @Override
    public void handleRequest(Request request) {
        if (request.getType().equals(INSERT)) {
            System.out.println("Handling insert request...");
        } else if (next != null) {
            next.handleRequest(request);
        }
    }

    @Override
    public void setNextHandler(Handler handler) {
        next = handler;
    }
}
