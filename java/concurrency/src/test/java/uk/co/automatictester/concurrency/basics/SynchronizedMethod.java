package uk.co.automatictester.concurrency.basics;

import org.testng.annotations.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SynchronizedMethod {

    private final int loopCount = 1_000_000;
    private final int threads = 4;

    @Test(invocationCount = 5)
    public void testIncrementSynchronizedV1() throws InterruptedException {
        Counter counter = new Counter();
        Runnable runnable = counter::incrementSynchronizedV1;
        test(counter, runnable);
    }

    @Test(invocationCount = 5)
    public void testIncrementSynchronizedV2() throws InterruptedException {
        Counter counter = new Counter();
        Runnable runnable = counter::incrementSynchronizedV2;
        test(counter, runnable);
    }

    @Test(invocationCount = 5, expectedExceptions = AssertionError.class)
    public void test() throws InterruptedException {
        Counter counter = new Counter();
        Runnable runnable = counter::increment;
        test(counter, runnable);
    }

    private void test(Counter counter, Runnable runnable) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < loopCount; i++) {
            service.submit(runnable);
        }
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
        assertThat(counter.get(), equalTo(loopCount));
    }
}

class Counter {

    private int i = 0;

    public synchronized void incrementSynchronizedV1() {
        i++;
    }

    public void incrementSynchronizedV2() {
        synchronized (this) {
            i++;
        }
    }

    public void increment() {
        i++;
    }

    public int get() {
        return i;
    }
}
