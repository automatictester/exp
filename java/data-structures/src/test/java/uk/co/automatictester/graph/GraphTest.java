package uk.co.automatictester.graph;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

@SuppressWarnings("SimplifiedTestNGAssertion")
public class GraphTest {

    @Test
    public void testAddVertex() {
        Graph<String> graph = new Graph<>();
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
    public void testAddEdgeExSame() {
        Graph<String> graph = new Graph<>();
        Vertex<String> a = new Vertex<>("a");

        graph.addEdge(a, a);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddEdgeExNoSuchFrom() {
        Graph<String> graph = new Graph<>();
        Vertex<String> a = new Vertex<>("a");
        Vertex<String> b = new Vertex<>("b");
        graph.addVertex(b);
        graph.addEdge(a, b);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddEdgeExNoSuchTo() {
        Graph<String> graph = new Graph<>();
        Vertex<String> a = new Vertex<>("a");
        Vertex<String> b = new Vertex<>("b");
        graph.addVertex(a);
        graph.addEdge(a, b);
    }

    @Test
    public void testAddEdge() {
        Graph<String> graph = new Graph<>();
        Vertex<String> a = new Vertex<>("a");
        Vertex<String> b = new Vertex<>("b");
        graph.addVertex(a);
        graph.addVertex(b);
        boolean addedEdge = graph.addEdge(a, b);
        assertTrue(addedEdge);
        addedEdge = graph.addEdge(a, b);
        assertFalse(addedEdge);
        addedEdge = graph.addEdge(b, a);
        assertFalse(addedEdge);
        assertTrue(graph.edges.size() == 1);
    }

    @Test
    public void testEdgesOf() {
        Graph<String> graph = new Graph<>();
        Vertex<String> a = new Vertex<>("a");
        Vertex<String> b = new Vertex<>("b");
        Vertex<String> c = new Vertex<>("c");
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);

        assertTrue(graph.edgesOf(a).size() == 0);

        graph.addEdge(a, b);
        graph.addEdge(a, c);
        assertTrue(graph.edgesOf(a).size() == 2);
        assertTrue(graph.edgesOf(b).size() == 1);
        assertTrue(graph.edgesOf(c).size() == 1);
    }
}
