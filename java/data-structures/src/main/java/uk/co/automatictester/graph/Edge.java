package uk.co.automatictester.graph;

import java.util.Comparator;

@SuppressWarnings("WeakerAccess")
public class Edge<T extends Comparable<T>> implements Comparable<Edge<T>> {
    Vertex<T> from;
    Vertex<T> to;

    public Edge(Vertex<T> from, Vertex<T> to) {
        if (from.compareTo(to) < 0) {
            this.from = from;
            this.to = to;
        } else if (from.compareTo(to) > 0) {
            this.from = to;
            this.to = from;
        }
    }

    public int compareTo(Edge<T> other) {
        return Comparator.comparing((Edge<T> edge) -> edge.from)
                .thenComparing((Edge<T> edge) -> edge.to)
                .compare(this, other);
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", from.value, to.value);
    }
}
