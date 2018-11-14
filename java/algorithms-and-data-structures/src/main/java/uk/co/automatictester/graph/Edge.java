package uk.co.automatictester.graph;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Edge<T extends Comparable<T>> implements Comparable<Edge<T>> {
    private Vertex<T> from;
    private Vertex<T> to;

    public Edge(Vertex<T> from, Vertex<T> to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException();
        }
        if (from.compareTo(to) < 0) {
            this.from = from;
            this.to = to;
        } else if (from.compareTo(to) > 0) {
            this.from = to;
            this.to = from;
        }
    }

    public Vertex<T> from() {
        return from;
    }

    public Vertex<T> to() {
        return to;
    }

    public int compareTo(Edge<T> other) {
        return Comparator.comparing((Edge<T> edge) -> edge.from)
                .thenComparing((Edge<T> edge) -> edge.to)
                .compare(this, other);
    }

    public Set<Vertex<T>> vertices() {
        return Stream.of(to, from).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", from.value, to.value);
    }
}
