package uk.co.automatictester.behavioral.chainofresponsibility.handler;

import static uk.co.automatictester.behavioral.chainofresponsibility.handler.RequestType.INSERT;

public class InsertRequest implements Request {

    @Override
    public RequestType getType() {
        return INSERT;
    }
}
