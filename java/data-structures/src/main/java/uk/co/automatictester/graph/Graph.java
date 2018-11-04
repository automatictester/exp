package uk.co.automatictester.graph;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@SuppressWarnings("WeakerAccess")
public class Graph<T extends Comparable<T>> {

    private static final int ALL_CONECTIONS = -1;
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

    public Set<Vertex<T>> connectionsOf(Vertex<T> vertex, int degree) {
        if (degree == ALL_CONECTIONS) {
            degree = vertices.size() - 1;
        }
        Set<Vertex<T>> connections = connectionsOf(vertex);
        if (degree > 1) {
            for (Vertex<T> connection : connectionsOf(vertex)) {
                connections.addAll(connectionsOf(connection, degree - 1));
            }
        }
        connections.remove(vertex);
        return connections;
    }

    public Set<Vertex<T>> connectionsOf(Vertex<T> vertex) {
        Set<Vertex<T>> connections = edgesOf(vertex).stream().map(e -> e.from).filter(v -> !v.equals(vertex)).collect(Collectors.toSet());
        connections.addAll(edgesOf(vertex).stream().map(e -> e.to).filter(v -> !v.equals(vertex)).collect(Collectors.toSet()));
        return connections;
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
