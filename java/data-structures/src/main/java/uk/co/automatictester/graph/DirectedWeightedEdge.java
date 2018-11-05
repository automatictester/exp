package uk.co.automatictester.graph;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("WeakerAccess")
public class DirectedWeightedEdge<T extends Comparable<T>> implements Comparable<DirectedWeightedEdge<T>> {
    Vertex<T> from;
    Vertex<T> to;
    int weight;

    public DirectedWeightedEdge(Vertex<T> from, Vertex<T> to, int weight) {
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
    public int compareTo(DirectedWeightedEdge<T> other) {
        return Comparator.comparing((DirectedWeightedEdge<T> edge) -> edge.from)
                .thenComparing((DirectedWeightedEdge<T> edge) -> edge.to)
                .thenComparing((DirectedWeightedEdge<T> edge) -> edge.weight)
                .compare(this, other);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof DirectedWeightedEdge) {
            DirectedWeightedEdge other = ((DirectedWeightedEdge) o);
            return from.equals(other.from)
                    && to.equals(other.to)
                    && weight == other.weight;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return from.hashCode() + to.hashCode() + weight;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s, %d)", from.value, to.value, weight);
    }
}
