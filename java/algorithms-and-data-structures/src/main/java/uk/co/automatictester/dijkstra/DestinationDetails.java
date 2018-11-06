package uk.co.automatictester.dijkstra;

public class DestinationDetails {
    int cost;
    boolean visited = false;

    DestinationDetails(int cost) {
        this.cost = cost;
    }

    DestinationDetails(int cost, boolean visited) {
        this.cost = cost;
        this.visited = visited;
    }

    @Override
    public String toString() {
        return String.format("%d - %s", cost, visited);
    }
}
