package uk.co.automatictester.graph;

import java.util.Comparator;

public class Edge<T extends Comparable<T>> implements Comparable<Edge<T>> {
    Vertex<T> from;
    Vertex<T> to;

    public Edge(Vertex<T> from, Vertex<T> to) {
        this.from = from;
        this.to = to;
    }

    public int compareTo(Edge<T> other) {
        return Comparator.comparing((Edge<T> edge) -> edge.from)
                .thenComparing((Edge<T> edge) -> edge.to)
                .compare(this, other);
    }
}
