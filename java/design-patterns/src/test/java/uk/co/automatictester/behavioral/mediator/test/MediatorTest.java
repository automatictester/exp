package uk.co.automatictester.behavioral.mediator.test;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import uk.co.automatictester.behavioral.mediator.Client;
import uk.co.automatictester.behavioral.mediator.Server;

public class MediatorTest {

    private Server s;
    private Client james;
    private Client edward;
    private Client rob;

    @BeforeClass
    public void setup() {
        s = new Server();

        james = new Client("James");
        edward = new Client("Edward");
        rob = new Client("Rob");
    }

    @Test
    public void testMediator() {
        james.register(s);
        edward.register(s);

        james.send("Rob", "hey");
        james.send("Edward", "hi");

        sleep(150);
        edward.send("James", "hello");

        sleep(250);
        rob.register(s);

        sleep(150);
        rob.send("James", "yo");

        sleep(250);
    }


    @AfterClass
    public void shutdown() {
        james.deregister();
        edward.deregister();
        rob.deregister();

        s.shutdown();
    }

    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
