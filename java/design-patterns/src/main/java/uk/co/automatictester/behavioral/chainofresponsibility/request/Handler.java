package uk.co.automatictester.behavioral.chainofresponsibility.request;

import uk.co.automatictester.behavioral.chainofresponsibility.handler.Request;

public interface Handler {
    void handleRequest(Request request);
    void setNextHandler(Handler handler);
}
