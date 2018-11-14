package uk.co.automatictester.graph.test;

import org.testng.annotations.Test;
import uk.co.automatictester.graph.Edge;
import uk.co.automatictester.graph.Vertex;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.testng.Assert.assertTrue;

public class EdgeTest {

    @Test
    public void testNormalization() {
        Vertex<String> a = new Vertex<>("a");
        Vertex<String> b = new Vertex<>("b");
        Edge<String> edge = new Edge<>(b, a);
        assertTrue(edge.toString().equals("(a, b)"));
    }

    @Test
    public void testVertices() {
        Vertex<String> a = new Vertex<>("a");
        Vertex<String> b = new Vertex<>("b");
        Edge<String> edge = new Edge<>(b, a);
        Object[] expected = Stream.of(a, b).distinct().toArray();
        assertThat(edge.vertices(), containsInAnyOrder(expected));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSameToFrom() {
        Vertex<String> a = new Vertex<>("a");
        new Edge<>(a, a);
    }
}
