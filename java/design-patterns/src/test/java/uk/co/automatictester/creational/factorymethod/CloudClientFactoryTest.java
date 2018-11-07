package uk.co.automatictester.creational.factorymethod;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static uk.co.automatictester.creational.factorymethod.ClientType.MOCKED;
import static uk.co.automatictester.creational.factorymethod.ClientType.REAL;

public class CloudClientFactoryTest {

    @Test
    public void testFactoryMethod() {
        CloudClient mockedClient = CloudClientFactory.getInstance(MOCKED);
        CloudClient anotherMockedClient = CloudClientFactory.getInstance(MOCKED);
        CloudClient realClient = CloudClientFactory.getInstance(REAL);

        assertEquals(mockedClient, anotherMockedClient);
        assertNotEquals(mockedClient, realClient);
    }
}
