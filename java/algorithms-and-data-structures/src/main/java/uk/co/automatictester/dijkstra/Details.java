package uk.co.automatictester.dijkstra;

public class Details<T extends Comparable<T>> {
    int cost;
    boolean visited = false;

    Details(int cost) {
        this.cost = cost;
    }

    Details(int cost, boolean visited) {
        this.cost = cost;
        this.visited = visited;
    }

    @Override
    public String toString() {
        return String.format("%d - %s", cost, visited);
    }
}
