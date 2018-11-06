package uk.co.automatictester.dijkstra;

import org.testng.annotations.Test;
import uk.co.automatictester.graph.DirectedWeightedGraph;
import uk.co.automatictester.graph.Vertex;

import java.util.Map;

import static org.testng.Assert.assertTrue;

@SuppressWarnings("SimplifiedTestNGAssertion")
public class DijkstraSolverTest {

    @Test
    public void testGo() {
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

        DijkstraSolver<String> dijkstra = new DijkstraSolver<>(graph, a);
        Map<Vertex<String>, DestinationDetails> destinations = dijkstra.go();
        assertTrue(destinations.get(b).cost == 100);
        assertTrue(destinations.get(c).cost == 200);
        assertTrue(destinations.get(d).cost == 160);
        assertTrue(destinations.get(e).cost == 280);
    }

    @Test
    public void testGo2() {
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

        DijkstraSolver<String> dijkstra = new DijkstraSolver<>(graph, c);
        Map<Vertex<String>, DestinationDetails> destinations = dijkstra.go();
        assertTrue(destinations.get(b).cost == 180);
        assertTrue(destinations.get(d).cost == 360);
        assertTrue(destinations.get(e).cost == 80);
    }
}