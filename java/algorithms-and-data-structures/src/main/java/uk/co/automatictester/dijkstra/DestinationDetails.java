package uk.co.automatictester.dijkstra;

public class DestinationDetails {
    private int cost;
    private boolean visited = false;

    public DestinationDetails(int cost) {
        this.cost = cost;
    }

    public DestinationDetails(int cost, boolean visited) {
        this.cost = cost;
        this.visited = visited;
    }

    public int cost() {
        return cost;
    }

    public boolean getVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @Override
    public String toString() {
        return String.format("%d - %s", cost, visited);
    }
}
