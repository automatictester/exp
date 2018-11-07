package uk.co.automatictester.structural.flyweight;

import org.testng.annotations.Test;

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
