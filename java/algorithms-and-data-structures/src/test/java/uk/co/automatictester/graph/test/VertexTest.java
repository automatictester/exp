package uk.co.automatictester.graph.test;

import org.testng.annotations.Test;
import uk.co.automatictester.graph.Vertex;

import static org.testng.Assert.assertTrue;

public class VertexTest {

    @Test
    public void testToString() {
        Vertex<String> a = new Vertex<>("a");
        assertTrue(a.toString().equals("a"));
    }
}
