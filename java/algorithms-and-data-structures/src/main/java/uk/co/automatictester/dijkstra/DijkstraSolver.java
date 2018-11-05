package uk.co.automatictester.dijkstra;

import uk.co.automatictester.graph.DirectedWeightedEdge;
import uk.co.automatictester.graph.DirectedWeightedGraph;
import uk.co.automatictester.graph.Vertex;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("WeakerAccess")
public class DijkstraSolver<T extends Comparable<T>> {

    private DirectedWeightedGraph<T> graph;
    // TODO: move destinations to its own class
    private Map<Vertex<T>, Details> destinations = new HashMap<>();
    private Vertex<T> entry;

    public DijkstraSolver(DirectedWeightedGraph<T> graph, Vertex<T> entry) {
        this.graph = graph;
        this.entry = entry;
    }

    public void go() {
        Vertex<T> currentVertex = entry;
        int cost = 0;
        Details<T> details = new Details<>(cost, true);
        destinations.put(currentVertex, details);
        Set<DirectedWeightedEdge<T>> edgesFrom;

        edgesFrom = graph.edgesFrom(currentVertex);
        updateDestinations(edgesFrom, cost);

        currentVertex = cheapestUnvisitedDestination();
        destinations.get(currentVertex).visited = true;
        cost = destinations.get(currentVertex).cost;
        edgesFrom = graph.edgesFrom(currentVertex);
        updateDestinations(edgesFrom, cost);

        currentVertex = cheapestUnvisitedDestination();
        destinations.get(currentVertex).visited = true;
        cost = destinations.get(currentVertex).cost;
        edgesFrom = graph.edgesFrom(currentVertex);
        updateDestinations(edgesFrom, cost);

        currentVertex = cheapestUnvisitedDestination();
        destinations.get(currentVertex).visited = true;
        cost = destinations.get(currentVertex).cost;
        edgesFrom = graph.edgesFrom(currentVertex);
        updateDestinations(edgesFrom, cost);

        currentVertex = cheapestUnvisitedDestination();
        destinations.get(currentVertex).visited = true;
        cost = destinations.get(currentVertex).cost;
        edgesFrom = graph.edgesFrom(currentVertex);
        updateDestinations(edgesFrom, cost);
    }

    private Vertex<T> cheapestUnvisitedDestination() {
        Vertex<T> minCostDestination = null;
        int minCost = -1;
        for (Vertex<T> key : destinations.keySet()) {
            if (destinations.get(key).visited) continue;
            int destinationCost = destinations.get(key).cost;
            if (minCost == -1 || destinationCost < minCost) {
                minCostDestination = key;
                minCost = destinationCost;
            }
        }
        return minCostDestination;
    }

    private void updateDestinations(Set<DirectedWeightedEdge<T>> edges, int cost) {
        for (DirectedWeightedEdge<T> edge : edges) {
            Vertex<T> to = edge.to;
            Details details;
            if (!destinations.containsKey(to)) {
                details = new Details<>(edge.weight + cost);
                destinations.put(to, details);
            } else if (destinations.containsKey(to) && edge.weight + cost < destinations.get(to).cost) {
                details = new Details<>(edge.weight + cost);
                destinations.put(to, details);
            }
        }
        showResults();
    }

    private void showResults() {
        for (Vertex<T> key : destinations.keySet()) {
            if (destinations.get(key).cost == 0) continue;
            String destination = String.format("%s -> %s", key.value, destinations.get(key));
            System.out.println(destination);
        }
        System.out.println("==================");
    }
}
