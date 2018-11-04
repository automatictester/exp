package uk.co.automatictester.graph;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.TreeSet;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

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

    @Test
    public void testDeleteEdge() {
        Graph<String> graph = new Graph<>();
        Vertex<String> a = new Vertex<>("a");
        Vertex<String> b = new Vertex<>("b");
        Vertex<String> c = new Vertex<>("c");
        Vertex<String> d = new Vertex<>("d");
        Vertex<String> e = new Vertex<>("e");
        Vertex<String> f = new Vertex<>("f");
        Vertex<String> g = new Vertex<>("g");
        Vertex<String> h = new Vertex<>("h");
        Vertex<String> i = new Vertex<>("i");
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addVertex(e);
        graph.addVertex(f);
        graph.addVertex(g);
        graph.addVertex(h);
        graph.addVertex(i);
        graph.addEdge(a, b);
        graph.addEdge(c, a);
        graph.addEdge(a, d);
        graph.addEdge(e, a);
        graph.addEdge(b, f);
        graph.addEdge(d, g);
        graph.addEdge(f, g);
        graph.addEdge(f, h);
        graph.addEdge(g, i);
        graph.addEdge(e, i);

        assertTrue(graph.edges.size() == 10);
        assertTrue(graph.edgesOf(a).contains(new Edge<>(a, c)));

        boolean deleted = graph.deleteEdge(a, c);
        assertTrue(deleted);
        deleted = graph.deleteEdge(e, a);
        assertTrue(deleted);
        assertTrue(graph.edges.size() == 8);
        assertFalse(graph.edgesOf(a).contains(new Edge<>(a, c)));
        assertFalse(graph.edgesOf(a).contains(new Edge<>(a, e)));
    }

    @Test
    public void testDeleteVertex() {
        Graph<String> graph = new Graph<>();
        Vertex<String> a = new Vertex<>("a");
        Vertex<String> b = new Vertex<>("b");
        Vertex<String> c = new Vertex<>("c");
        Vertex<String> d = new Vertex<>("d");
        Vertex<String> e = new Vertex<>("e");
        Vertex<String> f = new Vertex<>("f");
        Vertex<String> g = new Vertex<>("g");
        Vertex<String> h = new Vertex<>("h");
        Vertex<String> i = new Vertex<>("i");
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addVertex(e);
        graph.addVertex(f);
        graph.addVertex(g);
        graph.addVertex(h);
        graph.addVertex(i);
        graph.addEdge(a, b);
        graph.addEdge(c, a);
        graph.addEdge(a, d);
        graph.addEdge(e, a);
        graph.addEdge(b, f);
        graph.addEdge(d, g);
        graph.addEdge(f, g);
        graph.addEdge(f, h);
        graph.addEdge(g, i);
        graph.addEdge(e, i);

        assertTrue(graph.vertices.size() == 9);
        assertTrue(graph.edges.size() == 10);
        assertTrue(graph.edgesOf(a).contains(new Edge<>(a, c)));

        boolean deleted = graph.deleteVertex(a);
        assertTrue(deleted);
        assertTrue(graph.vertices.size() == 8);
        assertTrue(graph.edges.size() == 6);
        assertFalse(graph.edgesOf(a).contains(new Edge<>(a, c)));
    }

    @Test
    public void testGetNthConnections() {
        Graph<String> graph = new Graph<>();
        Vertex<String> a = new Vertex<>("a");
        Vertex<String> b = new Vertex<>("b");
        Vertex<String> c = new Vertex<>("c");
        Vertex<String> d = new Vertex<>("d");
        Vertex<String> e = new Vertex<>("e");
        Vertex<String> f = new Vertex<>("f");
        Vertex<String> g = new Vertex<>("g");
        Vertex<String> h = new Vertex<>("h");
        Vertex<String> i = new Vertex<>("i");
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addVertex(e);
        graph.addVertex(f);
        graph.addVertex(g);
        graph.addVertex(h);
        graph.addVertex(i);
        graph.addEdge(a, b);
        graph.addEdge(c, a);
        graph.addEdge(a, d);
        graph.addEdge(e, a);
        graph.addEdge(b, f);
        graph.addEdge(d, g);
        graph.addEdge(f, g);
        graph.addEdge(f, h);
        graph.addEdge(g, i);
        graph.addEdge(e, i);

        assertTrue(graph.connectionsOf(a, 1).equals(new TreeSet<>(Arrays.asList(b, c, d, e))));
        assertTrue(graph.connectionsOf(f, 1).equals(new TreeSet<>(Arrays.asList(b, g, h))));
        assertTrue(graph.connectionsOf(h, 2).equals(new TreeSet<>(Arrays.asList(b, f, g))));
        assertTrue(graph.connectionsOf(a, 2).equals(new TreeSet<>(Arrays.asList(b, c, d, e, f, g, i))));
        assertTrue(graph.connectionsOf(a, 3).equals(new TreeSet<>(Arrays.asList(b, c, d, e, f, g, h, i))));
    }
}
