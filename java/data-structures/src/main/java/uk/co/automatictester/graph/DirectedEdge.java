package uk.co.automatictester.graph;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("WeakerAccess")
public class DirectedEdge<T extends Comparable<T>> implements Comparable<DirectedEdge<T>> {
    Vertex<T> from;
    Vertex<T> to;
    int weight;

    public DirectedEdge(Vertex<T> from, Vertex<T> to, int weight) {
        if (from.equals(to)) {
            throw new IllegalArgumentException();
        }
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public int compareTo(DirectedEdge<T> other) {
        return Comparator.comparing((DirectedEdge<T> edge) -> edge.from)
                .thenComparing((DirectedEdge<T> edge) -> edge.to)
                .thenComparing((DirectedEdge<T> edge) -> edge.weight)
                .compare(this, other);
    }

    public Set<Vertex<T>> vertices() {
        return Stream.of(to, from).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return String.format("(%s, %s, %d)", from.value, to.value, weight);
    }
}
