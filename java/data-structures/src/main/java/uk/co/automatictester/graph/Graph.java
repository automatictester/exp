package uk.co.automatictester.graph;

import java.util.Set;
import java.util.TreeSet;

@SuppressWarnings("WeakerAccess")
public class Graph<T extends Comparable<T>> {

    TreeSet<Vertex<T>> vertices = new TreeSet<>();
    TreeSet<Edge<T>> edges = new TreeSet<>();

    public boolean addVertex(Vertex<T> vertex) {
        return vertices.add(vertex);
    }

    public boolean addEdge(Vertex<T> from, Vertex<T> to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException();
        }
        if (!vertices.contains(from) || !vertices.contains(to)) {
            throw new IllegalArgumentException();
        }
        if (!edges.contains(new Edge<>(to, from)) && !edges.contains(new Edge<>(from, to))) {
            Edge<T> edge = new Edge<>(from, to);
            return edges.add(edge);
        }
        return false;
    }

    public boolean deleteEdge(Vertex<T> from, Vertex<T> to) {
        return edges.remove(new Edge<T>(from, to));
    }

    public boolean deleteVertex(Vertex<T> vertex) {
        boolean vertexExists = vertices.contains(vertex);
        if (vertexExists) {
            vertices.remove(vertex);
            edgesOf(vertex).forEach(v -> edges.remove(v));
        }
        return vertexExists;
    }

    public Set<Edge<T>> edgesOf(Vertex<T> vertex) {
        Set<Edge<T>> edgesOfVertex = new TreeSet<>();
        for (Edge<T> edge : edges) {
            if (edge.from.equals(vertex)) {
                edgesOfVertex.add(edge);
            }
            if (edge.to.equals(vertex)) {
                edgesOfVertex.add(edge);
            }
        }
        return edgesOfVertex;
    }
}
