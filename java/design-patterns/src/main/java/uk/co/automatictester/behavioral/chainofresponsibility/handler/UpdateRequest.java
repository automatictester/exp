package uk.co.automatictester.behavioral.chainofresponsibility.handler;

import static uk.co.automatictester.behavioral.chainofresponsibility.handler.RequestType.UPDATE;

public class UpdateRequest implements Request {

    @Override
    public RequestType getType() {
        return UPDATE;
    }
}
