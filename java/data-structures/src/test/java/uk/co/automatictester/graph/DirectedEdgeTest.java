package uk.co.automatictester.graph;

import org.testng.annotations.Test;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.testng.Assert.assertTrue;

public class DirectedEdgeTest {

    @Test
    public void testNoNormalization() {
        Vertex<String> a = new Vertex<>("a");
        Vertex<String> b = new Vertex<>("b");
        DirectedEdge<String> edge = new DirectedEdge<>(b, a, 10);
        assertTrue(edge.toString().equals("(b, a, 10)"));
    }

    @Test
    public void testVertices() {
        Vertex<String> a = new Vertex<>("a");
        Vertex<String> b = new Vertex<>("b");
        DirectedEdge<String> edge = new DirectedEdge<>(b, a, 10);
        Object[] expected = Stream.of(a, b).distinct().toArray();
        assertThat(edge.vertices(), containsInAnyOrder(expected));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSameToFrom() {
        Vertex<String> a = new Vertex<>("a");
        new DirectedEdge<>(a, a, 10);
    }
}
