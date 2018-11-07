package uk.co.automatictester.structural.flyweight;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CloudClientFactoryTest {

    @Test
    public void testFlyweightFactory() {
        CloudClient us = CloudClientFactory.getInstance("us");
        CloudClient eu = CloudClientFactory.getInstance("eu");
        CloudClient eu2 = CloudClientFactory.getInstance("eu");

        assertNotSame(us, eu);
        assertSame(eu, eu2);
    }
}
