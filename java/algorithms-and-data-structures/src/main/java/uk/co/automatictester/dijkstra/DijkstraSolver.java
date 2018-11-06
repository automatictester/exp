package uk.co.automatictester.dijkstra;

import uk.co.automatictester.graph.DirectedWeightedEdge;
import uk.co.automatictester.graph.DirectedWeightedGraph;
import uk.co.automatictester.graph.Vertex;

import java.util.Map;
import java.util.Set;

@SuppressWarnings("WeakerAccess")
public class DijkstraSolver<T extends Comparable<T>> {

    private DirectedWeightedGraph<T> graph;
    private Vertex<T> entry;
    private Destinations<T> destinations = new Destinations<>();

    public DijkstraSolver(DirectedWeightedGraph<T> graph, Vertex<T> entry) {
        this.graph = graph;
        this.entry = entry;
    }

    public Map<Vertex<T>, DestinationDetails> go() {
        Vertex<T> currentVertex = entry;
        int cost = 0;
        DestinationDetails details = new DestinationDetails(cost, true);
        destinations.add(currentVertex, details);
        Set<DirectedWeightedEdge<T>> edgesFrom;

        edgesFrom = graph.edgesFrom(currentVertex);
        destinations.updateDestinations(edgesFrom, cost);

        do {
            currentVertex = destinations.cheapestUnvisitedDestination();
            destinations.get(currentVertex).visited = true;
            cost = destinations.get(currentVertex).cost;
            edgesFrom = graph.edgesFrom(currentVertex);
            destinations.updateDestinations(edgesFrom, cost);

        } while (destinations.hasUnvisitedDestinations());

        return destinations.getAll();
    }
}
