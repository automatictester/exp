package uk.co.automatictester.graph;

@SuppressWarnings("WeakerAccess")
public class Vertex<T extends Comparable<T>> implements Comparable<Vertex<T>> {
    T value;

    public Vertex(T value) {
        this.value = value;
    }

    @Override
    public int compareTo(Vertex<T> other) {
        return this.value.compareTo(other.value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
