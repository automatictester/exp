package uk.co.automatictester.graph;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class VertexTest {

    @Test
    public void testToString() {
        Vertex<String> a = new Vertex<>("a");
        assertTrue(a.toString().equals("a"));
    }
}
