package uk.co.automatictester.concurrency.basics;

import org.testng.annotations.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SynchronizedMethod {

    private final int loopCount = 1_000_000;
    private final int threads = 4;

    @Test(invocationCount = 5)
    public void testIncrementSynchronizedV1() throws InterruptedException {
        Counter counter = new Counter();
        Consumer<Counter> consumer = Counter::incrementSynchronizedV1;
        test(counter, consumer);
        assertThat(counter.get(), equalTo(loopCount));
    }

    @Test(invocationCount = 5)
    public void testIncrementSynchronizedV2() throws InterruptedException {
        Counter counter = new Counter();
        Consumer<Counter> consumer = Counter::incrementSynchronizedV2;
        test(counter, consumer);
        assertThat(counter.get(), equalTo(loopCount));
    }

    @Test(invocationCount = 5)
    public void test() throws InterruptedException {
        Counter counter = new Counter();
        Consumer<Counter> consumer = Counter::increment;
        test(counter, consumer);
        assertThat(counter.get(), not(equalTo(loopCount)));
    }

    private void test(Counter counter, Consumer<Counter> consumer) throws InterruptedException {
        Runnable runnable = () -> {
            consumer.accept(counter);
        };

        ExecutorService service = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < loopCount; i++) {
            service.submit(runnable);
        }
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
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
