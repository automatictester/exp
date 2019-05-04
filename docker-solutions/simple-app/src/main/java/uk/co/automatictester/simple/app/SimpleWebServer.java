package uk.co.automatictester.simple.app;

import io.javalin.Javalin;

public class SimpleWebServer {

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8080);
        app.get("/", ctx -> ctx.result("This is a response"));
    }
}
