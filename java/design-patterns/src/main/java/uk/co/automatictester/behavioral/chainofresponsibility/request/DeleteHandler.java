package uk.co.automatictester.behavioral.chainofresponsibility.request;

import uk.co.automatictester.behavioral.chainofresponsibility.handler.Request;

import static uk.co.automatictester.behavioral.chainofresponsibility.handler.RequestType.DELETE;

public class DeleteHandler implements Handler {

    private Handler next;

    @Override
    public void handleRequest(Request request) {
        if (request.getType().equals(DELETE)) {
            System.out.println("Handling delete request...");
        } else if (next != null) {
            next.handleRequest(request);
        }
    }

    @Override
    public void setNextHandler(Handler handler) {
        next = handler;
    }
}
