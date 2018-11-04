package uk.co.automatictester.graph;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class EdgeTest {

    @Test
    public void testNormalization() {
        Vertex<String> a = new Vertex<>("a");
        Vertex<String> b = new Vertex<>("b");
        Edge<String> edge = new Edge<>(b, a);
        assertTrue(edge.toString().equals("(a, b)"));
    }
}