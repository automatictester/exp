package uk.co.automatictester.dijkstra;

import uk.co.automatictester.graph.DirectedWeightedEdge;
import uk.co.automatictester.graph.Vertex;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("WeakerAccess")
public class Destinations<T extends Comparable<T>> {

    private Map<Vertex<T>, DestinationDetails> destinations = new HashMap<>();

    public void add(Vertex<T> destination, DestinationDetails details) {
        destinations.put(destination, details);
    }

    public Vertex<T> cheapestUnvisitedDestination() {
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

    public DestinationDetails get(Vertex<T> destination) {
        return destinations.get(destination);
    }

    public Map<Vertex<T>, DestinationDetails> getAll() {
        return destinations;
    }

    public boolean hasUnvisitedDestinations() {
        for (Vertex<T> key : destinations.keySet()) {
            if (!destinations.get(key).visited) return true;
        }
        return false;
    }

    public void showResults() {
        for (Vertex<T> key : destinations.keySet()) {
            if (destinations.get(key).cost == 0) continue;
            String destination = String.format("%s -> %s", key.value, destinations.get(key));
            System.out.println(destination);
        }
        System.out.println("==================");
    }

    public void updateDestinations(Set<DirectedWeightedEdge<T>> edges, int cost) {
        for (DirectedWeightedEdge<T> edge : edges) {
            Vertex<T> to = edge.to;
            DestinationDetails details;
            if (!destinations.containsKey(to)) {
                details = new DestinationDetails(edge.weight + cost);
                destinations.put(to, details);
            } else if (destinations.containsKey(to) && edge.weight + cost < destinations.get(to).cost) {
                details = new DestinationDetails(edge.weight + cost);
                destinations.put(to, details);
            }
        }
        showResults();
    }
}
