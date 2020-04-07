package uk.co.automatictester.concurrency.basics;

import org.testng.annotations.Test;

public class SynchronizedVolatile {

    private final int loopCount = 100_000_000;

    @Test(invocationCount = 5, description = "volatile is much faster than synchronized")
    public void testGetVolatile() {
        State state = new State();
        for (int i = 0; i < loopCount; i++) {
            state.getV();
        }
    }

    @Test(invocationCount = 5)
    public void testGetSynchronized() {
        State state = new State();
        for (int i = 0; i < loopCount; i++) {
            state.getS();
        }
    }
}

class State {

    private volatile int v = 0;
    private int s = 0;

    public int getV() {
        return v;
    }

    public synchronized int getS() {
        return s;
    }
}
