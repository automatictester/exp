package uk.co.automatictester.dijkstra;

import org.testng.annotations.Test;
import uk.co.automatictester.graph.DirectedWeightedEdge;
import uk.co.automatictester.graph.Vertex;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static org.testng.Assert.assertTrue;

@SuppressWarnings("SimplifiedTestNGAssertion")
public class DestinationsTest {

    @Test
    public void testCheapestUnvisitedDestination() {
        Vertex<String> a = new Vertex<>("a");
        Vertex<String> b = new Vertex<>("b");
        Vertex<String> c = new Vertex<>("c");

        DestinationDetails detailsA = new DestinationDetails(20, true);
        DestinationDetails detailsB = new DestinationDetails(30);
        DestinationDetails detailsC = new DestinationDetails(40);

        Destinations<String> destinations = new Destinations<>();
        destinations.add(a, detailsA);
        destinations.add(b, detailsB);
        destinations.add(c, detailsC);

        assertTrue(destinations.cheapestUnvisitedDestination().equals(b));
    }

    @Test
    public void testGet() {
        Vertex<String> a = new Vertex<>("a");
        Vertex<String> b = new Vertex<>("b");
        Vertex<String> c = new Vertex<>("c");

        DestinationDetails detailsA = new DestinationDetails(20, true);
        DestinationDetails detailsB = new DestinationDetails(30);
        DestinationDetails detailsC = new DestinationDetails(40);

        Destinations<String> destinations = new Destinations<>();
        destinations.add(a, detailsA);
        destinations.add(b, detailsB);
        destinations.add(c, detailsC);

        assertTrue(destinations.get(b).equals(detailsB));
    }

    @Test
    public void testGetAll() {
        Vertex<String> a = new Vertex<>("a");
        Vertex<String> b = new Vertex<>("b");
        Vertex<String> c = new Vertex<>("c");

        DestinationDetails detailsA = new DestinationDetails(20, true);
        DestinationDetails detailsB = new DestinationDetails(30);
        DestinationDetails detailsC = new DestinationDetails(40);

        Destinations<String> destinations = new Destinations<>();
        destinations.add(a, detailsA);
        destinations.add(b, detailsB);
        destinations.add(c, detailsC);

        Map<Vertex<String>, DestinationDetails> allDestinations = destinations.getAll();
        assertTrue(allDestinations.get(a).equals(detailsA));
        assertTrue(allDestinations.get(b).equals(detailsB));
        assertTrue(allDestinations.get(c).equals(detailsC));
    }

    @Test
    public void testHasUnvisitedDestinations() {
        Vertex<String> a = new Vertex<>("a");
        Vertex<String> b = new Vertex<>("b");
        Vertex<String> c = new Vertex<>("c");

        DestinationDetails detailsA = new DestinationDetails(20, true);
        DestinationDetails detailsB = new DestinationDetails(30);
        DestinationDetails detailsC = new DestinationDetails(40);

        Destinations<String> destinations = new Destinations<>();
        destinations.add(a, detailsA);

        assertTrue(!destinations.hasUnvisitedDestinations());

        destinations.add(b, detailsB);
        destinations.add(c, detailsC);
        assertTrue(destinations.hasUnvisitedDestinations());
    }

    @Test
    public void testUpdateDestinations() {
        Vertex<String> a = new Vertex<>("a");
        Vertex<String> b = new Vertex<>("b");
        Vertex<String> c = new Vertex<>("c");
        Vertex<String> d = new Vertex<>("d");

        DestinationDetails detailsA = new DestinationDetails(0);
        DestinationDetails detailsB = new DestinationDetails(200);
        DestinationDetails detailsC = new DestinationDetails(300);

        Destinations<String> destinations = new Destinations<>();
        destinations.add(a, detailsA);
        destinations.add(b, detailsB);
        destinations.add(c, detailsC);

        DirectedWeightedEdge<String> edgeB = new DirectedWeightedEdge<>(a, b, 120);
        DirectedWeightedEdge<String> edgeC = new DirectedWeightedEdge<>(a, c, 180);
        DirectedWeightedEdge<String> edgeD = new DirectedWeightedEdge<>(a, d, 500);

        Set<DirectedWeightedEdge<String>> edges = new TreeSet<>();
        edges.add(edgeB);
        edges.add(edgeC);
        edges.add(edgeD);

        destinations.updateDestinations(edges, 100);

        assertTrue(destinations.get(b).cost == 200);
        assertTrue(destinations.get(c).cost == 280);
        assertTrue(destinations.get(d).cost == 600);
    }
}
