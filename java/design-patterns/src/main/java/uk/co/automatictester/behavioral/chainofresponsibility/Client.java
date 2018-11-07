package uk.co.automatictester.behavioral.chainofresponsibility;

import uk.co.automatictester.behavioral.chainofresponsibility.handler.DeleteRequest;
import uk.co.automatictester.behavioral.chainofresponsibility.handler.InsertRequest;
import uk.co.automatictester.behavioral.chainofresponsibility.handler.UpdateRequest;
import uk.co.automatictester.behavioral.chainofresponsibility.request.DeleteHandler;
import uk.co.automatictester.behavioral.chainofresponsibility.request.Handler;
import uk.co.automatictester.behavioral.chainofresponsibility.request.InsertHandler;

public class Client {

    public static void main(String[] args) {

        Handler insertHandler = new InsertHandler();
        Handler deleteHandler = new DeleteHandler();

        insertHandler.setNextHandler(deleteHandler);

        InsertRequest insertOne = new InsertRequest();
        InsertRequest insertTwo = new InsertRequest();
        DeleteRequest delete = new DeleteRequest();
        UpdateRequest update = new UpdateRequest();

        insertHandler.handleRequest(insertOne);
        insertHandler.handleRequest(insertTwo);
        insertHandler.handleRequest(delete);
        insertHandler.handleRequest(update);
    }
}
