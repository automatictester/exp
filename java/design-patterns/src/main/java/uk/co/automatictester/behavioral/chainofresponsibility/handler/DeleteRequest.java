package uk.co.automatictester.behavioral.chainofresponsibility.handler;

import static uk.co.automatictester.behavioral.chainofresponsibility.handler.RequestType.DELETE;

public class DeleteRequest implements Request {

    @Override
    public RequestType getType() {
        return DELETE;
    }
}
