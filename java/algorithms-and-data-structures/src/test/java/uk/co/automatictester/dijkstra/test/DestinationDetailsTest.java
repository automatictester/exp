package uk.co.automatictester.dijkstra.test;

import org.testng.annotations.Test;
import uk.co.automatictester.dijkstra.DestinationDetails;

import static org.testng.Assert.assertTrue;

@SuppressWarnings("SimplifiedTestNGAssertion")
public class DestinationDetailsTest {

    @Test
    public void testToString() {
        DestinationDetails details = new DestinationDetails(10);
        assertTrue(details.toString().equals("10 - false"));
    }

    @Test
    public void testToStringTwoArgConstructor() {
        DestinationDetails details = new DestinationDetails(20, true);
        assertTrue(details.toString().equals("20 - true"));
    }
}
