package uk.co.automatictester.structural.flyweight.test;

import org.testng.annotations.Test;
import uk.co.automatictester.structural.flyweight.CloudClient;
import uk.co.automatictester.structural.flyweight.CloudClientFlyweightFactory;

import static org.testng.Assert.*;

public class CloudClientFlyweightFactoryTest {

    @Test
    public void testFlyweightFactory() {
        CloudClient us = CloudClientFlyweightFactory.getInstance("us");
        CloudClient eu = CloudClientFlyweightFactory.getInstance("eu");
        CloudClient eu2 = CloudClientFlyweightFactory.getInstance("eu");

        assertNotSame(us, eu);
        assertSame(eu, eu2);
    }
}
