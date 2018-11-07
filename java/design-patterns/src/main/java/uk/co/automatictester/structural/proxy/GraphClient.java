package uk.co.automatictester.structural.proxy;

public class GraphClient {

    public static void main(String[] args) {
        Graph graph = new GraphProxy();
        int cost = graph.shortestPathCost("Alicante-Bilbao");
    }
}
