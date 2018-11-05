package uk.co.automatictester.graph;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.TreeSet;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@SuppressWarnings("SimplifiedTestNGAssertion")
public class DirectedWeightedGraphTest {

    @Test
    public void testAddVertex() {
        DirectedWeightedGraph<String> graph = new DirectedWeightedGraph<>();
        Vertex<String> a = new Vertex<>("a");

        boolean aAdded = graph.addVertex(a);
        assertTrue(aAdded);

        aAdded = graph.addVertex(a);
        assertFalse(aAdded);

        Vertex<String> b = new Vertex<>("b");
        graph.addVertex(b);

        assertTrue(graph.vertices.size() == 2);
        assertTrue(graph.vertices.contains(a));
        assertTrue(graph.vertices.contains(b));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddEdgeExNoSuchFrom() {
        DirectedWeightedGraph<String> graph = new DirectedWeightedGraph<>();
        Vertex<String> a = new Vertex<>("a");
        Vertex<String> b = new Vertex<>("b");
        graph.addVertex(b);
        graph.addEdge(a, b, 10);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddEdgeExNoSuchTo() {
        DirectedWeightedGraph<String> graph = new DirectedWeightedGraph<>();
        Vertex<String> a = new Vertex<>("a");
        Vertex<String> b = new Vertex<>("b");
        graph.addVertex(a);
        graph.addEdge(a, b, 10);
    }

    @Test
    public void testAddEdge() {
        DirectedWeightedGraph<String> graph = new DirectedWeightedGraph<>();
        Vertex<String> a = new Vertex<>("a");
        Vertex<String> b = new Vertex<>("b");
        graph.addVertex(a);
        graph.addVertex(b);

        boolean addedEdge = graph.addEdge(a, b, 10);
        assertTrue(addedEdge);

        addedEdge = graph.addEdge(a, b, 10);
        assertFalse(addedEdge);

        addedEdge = graph.addEdge(b, a, 10);
        assertTrue(addedEdge);

        assertTrue(graph.edges.size() == 2);
    }

    @Test
    public void testEdgesOf() {
        DirectedWeightedGraph<String> graph = new DirectedWeightedGraph<>();
        Vertex<String> a = new Vertex<>("a");
        Vertex<String> b = new Vertex<>("b");
        Vertex<String> c = new Vertex<>("c");
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);

        assertTrue(graph.edgesOf(a).size() == 0);

        graph.addEdge(a, b, 10);
        graph.addEdge(a, c, 10);
        assertTrue(graph.edgesOf(a).size() == 2);
        assertTrue(graph.edgesOf(b).size() == 1);
        assertTrue(graph.edgesOf(c).size() == 1);
    }

    @Test
    public void testDeleteEdge() {
        DirectedWeightedGraph<String> graph = new DirectedWeightedGraph<>();
        Vertex<String> a = new Vertex<>("a");
        Vertex<String> b = new Vertex<>("b");
        Vertex<String> c = new Vertex<>("c");
        Vertex<String> d = new Vertex<>("d");
        Vertex<String> e = new Vertex<>("e");
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addVertex(e);
        graph.addEdge(a, b, 100);
        graph.addEdge(a, d, 160);
        graph.addEdge(b, c, 120);
        graph.addEdge(b, d, 180);
        graph.addEdge(c, e, 80);
        graph.addEdge(d, c, 40);
        graph.addEdge(d, e, 140);
        graph.addEdge(e, b, 100);

        assertTrue(graph.edges.size() == 8);
        assertTrue(graph.edges.contains(new DirectedEdge<>(a, b, 100)));

        boolean deleted = graph.deleteEdge(a, b, 100);
        assertTrue(deleted);

        deleted = graph.deleteEdge(c, b, 120);
        assertFalse(deleted);

        assertTrue(graph.edges.size() == 7);
        assertFalse(graph.edges.contains(new DirectedEdge<>(a, b, 100)));
        assertTrue(graph.edges.contains(new DirectedEdge<>(b, c, 120)));
    }

    @Test
    public void testDeleteVertex() {
        DirectedWeightedGraph<String> graph = new DirectedWeightedGraph<>();
        Vertex<String> a = new Vertex<>("a");
        Vertex<String> b = new Vertex<>("b");
        Vertex<String> c = new Vertex<>("c");
        Vertex<String> d = new Vertex<>("d");
        Vertex<String> e = new Vertex<>("e");
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addVertex(e);
        graph.addEdge(a, b, 100);
        graph.addEdge(a, d, 160);
        graph.addEdge(b, c, 120);
        graph.addEdge(b, d, 180);
        graph.addEdge(c, e, 80);
        graph.addEdge(d, c, 40);
        graph.addEdge(d, e, 140);
        graph.addEdge(e, b, 100);

        assertTrue(graph.vertices.size() == 5);
        assertTrue(graph.edges.size() == 8);
        assertTrue(graph.edges.contains(new DirectedEdge<>(c, e, 80)));

        boolean deleted = graph.deleteVertex(c);
        assertTrue(deleted);
        assertTrue(graph.vertices.size() == 4);
        assertTrue(graph.edges.size() == 5);
        assertFalse(graph.edges.contains(new DirectedEdge<>(c, e, 80)));
    }

    @Test
    public void testGetNthConnections() {
        DirectedWeightedGraph<String> graph = new DirectedWeightedGraph<>();
        Vertex<String> a = new Vertex<>("a");
        Vertex<String> b = new Vertex<>("b");
        Vertex<String> c = new Vertex<>("c");
        Vertex<String> d = new Vertex<>("d");
        Vertex<String> e = new Vertex<>("e");
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addVertex(e);
        graph.addEdge(a, b, 100);
        graph.addEdge(a, d, 160);
        graph.addEdge(b, c, 120);
        graph.addEdge(b, d, 180);
        graph.addEdge(c, e, 80);
        graph.addEdge(d, c, 40);
        graph.addEdge(d, e, 140);
        graph.addEdge(e, b, 100);

        assertTrue(graph.connectionsOf(b, 1).equals(new TreeSet<>(Arrays.asList(c, d))));
        assertTrue(graph.connectionsOf(a, 2).equals(new TreeSet<>(Arrays.asList(b, c, d, e))));
        assertTrue(graph.connectionsOf(c, 3).equals(new TreeSet<>(Arrays.asList(b, d, e))));
        assertTrue(graph.connectionsOf(d, -1).equals(new TreeSet<>(Arrays.asList(b, c, e))));
        assertTrue(graph.connectionsOf(e, 10).equals(new TreeSet<>(Arrays.asList(b, c, d))));
    }
}
