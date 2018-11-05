package uk.co.automatictester.graph;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("WeakerAccess")
public class DirectedEdge<T extends Comparable<T>> {
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

    public Set<Vertex<T>> vertices() {
        return Stream.of(to, from).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return String.format("(%s, %s, %d)", from.value, to.value, weight);
    }
}
