package uk.co.automatictester.graph;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@SuppressWarnings("WeakerAccess")
public class DirectedWeightedGraph<T extends Comparable<T>> {

    private static final int ALL_CONECTIONS = -1;
    TreeSet<Vertex<T>> vertices = new TreeSet<>();
    TreeSet<DirectedWeightedEdge<T>> edges = new TreeSet<>();

    public boolean addVertex(Vertex<T> vertex) {
        return vertices.add(vertex);
    }

    public boolean addEdge(Vertex<T> from, Vertex<T> to, int weight) {
        if (!vertices.contains(from) || !vertices.contains(to)) {
            throw new IllegalArgumentException();
        }
        DirectedWeightedEdge<T> edge = new DirectedWeightedEdge<>(from, to, weight);
        return edges.add(edge);
    }

    public boolean deleteEdge(Vertex<T> from, Vertex<T> to, int weight) {
        return edges.remove(new DirectedWeightedEdge<>(from, to, weight));
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
        Set<Vertex<T>> connections = new TreeSet<>();
        connections.add(vertex);

        for (int i = 0; i < degree; i++) {
            int connectionsBefore = connections.size();
            Set<Vertex<T>> nextDegreeConnections = new TreeSet<>();
            for (Vertex<T> connection : connections) {
                nextDegreeConnections.addAll(connectionsOf(connection));
            }
            connections.addAll(nextDegreeConnections);
            int connectionsAfter = connections.size();
            if (connectionsAfter == connectionsBefore) break;
        }
        connections.remove(vertex);
        return connections;
    }

    public Set<Vertex<T>> connectionsOf(Vertex<T> vertex) {
        return edgesOf(vertex).stream()
                .map(e -> e.to)
                .filter(v -> !v.equals(vertex))
                .collect(Collectors.toSet());
    }

    public Set<DirectedWeightedEdge<T>> edgesOf(Vertex<T> vertex) {
        Set<DirectedWeightedEdge<T>> edgesOfVertex = new TreeSet<>();
        for (DirectedWeightedEdge<T> edge : edges) {
            if (edge.from.equals(vertex)) {
                edgesOfVertex.add(edge);
            }
            if (edge.to.equals(vertex)) {
                edgesOfVertex.add(edge);
            }
        }
        return edgesOfVertex;
    }

    public Set<DirectedWeightedEdge<T>> edgesFrom(Vertex<T> vertex) {
        Set<DirectedWeightedEdge<T>> edgesFromVertex = new TreeSet<>();
        for (DirectedWeightedEdge<T> edge : edges) {
            if (edge.from.equals(vertex)) {
                edgesFromVertex.add(edge);
            }
        }
        return edgesFromVertex;
    }

    public DirectedWeightedEdge<T> cheapestEdgeToUnvisitedConnection(Vertex<T> from, Set<Vertex<T>> visitedConnections) {
        Set<DirectedWeightedEdge<T>> edgesFrom = edgesFrom(from);
        Set<DirectedWeightedEdge<T>> edgesToUnvisitedConnections = edgesFrom.stream()
                .filter(e -> !visitedConnections.contains(e.to))
                .collect(Collectors.toSet());
        if (edgesToUnvisitedConnections.size() == 0) {
            return null;
        } else {
            Comparator<DirectedWeightedEdge> comp = Comparator.comparingInt((DirectedWeightedEdge e) -> e.weight);
            Optional<DirectedWeightedEdge<T>> edge = edgesToUnvisitedConnections.stream().min(comp);
            return edge.get();
        }
    }
}