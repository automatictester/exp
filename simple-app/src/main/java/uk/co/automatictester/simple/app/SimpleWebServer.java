package uk.co.automatictester.simple.app;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class SimpleWebServer {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(getPortNumber()), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null);
        server.start();
    }

    private static int getPortNumber() {
        String port = System.getProperty("port", "8080");
        System.out.println("Starting server on port: " + port);
        return Integer.parseInt(port);
    }

    static class MyHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "This is a response";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
