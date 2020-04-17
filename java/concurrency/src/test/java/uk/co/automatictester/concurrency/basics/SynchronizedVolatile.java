package uk.co.automatictester.concurrency.basics;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class SynchronizedVolatile {

    private final int loopCount = 1_000_000;

    @Test(invocationCount = 5)
    public void testGetVolatile() {
        State state = new State();
        for (int i = 0; i < loopCount; i++) {
            int v = state.getVolatile();
            doSomething(v);
        }
    }

    @Test(invocationCount = 5)
    public void testGetSynchronized() {
        State state = new State();
        for (int i = 0; i < loopCount; i++) {
            int s = state.getSynchronized();
            doSomething(s);
        }
    }

    private void doSomething(int i) {
        if (i == ThreadLocalRandom.current().nextInt()) {
            log.info("x");
        }
    }
}

class State {

    private volatile int v = 0;
    private int s = 0;

    public int getVolatile() {
        return v;
    }

    public synchronized int getSynchronized() {
        return s;
    }
}
