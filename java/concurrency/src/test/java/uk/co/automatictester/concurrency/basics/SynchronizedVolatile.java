package uk.co.automatictester.concurrency.basics;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Slf4j
public class SynchronizedVolatile {

    private final int loopCount = 1_000_000;
    private final int threads = 4;

    @Test(invocationCount = 5)
    public void testGetVolatile() throws InterruptedException {
        Function<State, Integer> function = state -> state.v;
        test(function);
    }

    @Test(invocationCount = 5)
    public void testGetSynchronized() throws InterruptedException {
        Function<State, Integer> function = State::getSynchronized;
        test(function);
    }

    private void test(Function<State, Integer> function) throws InterruptedException {
        State state = new State();
        Runnable runnable = () -> {
            for (int i = 0; i < loopCount; i++) {
                int s = function.apply(state);
                if (s == ThreadLocalRandom.current().nextInt()) {
                    log.info("x");
                }
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < threads; i++) {
            executor.submit(runnable);
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }
}

class State {

    public volatile int v = 0;
    private int s = 0;

    public synchronized int getSynchronized() {
        return s;
    }
}
