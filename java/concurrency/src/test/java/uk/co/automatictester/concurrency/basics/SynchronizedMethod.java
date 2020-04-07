package uk.co.automatictester.concurrency.basics;

import org.testng.annotations.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class SynchronizedMethod {

    private final int invocationCount = 1_000_000;
    private final int threadPool = 4;

    @Test(invocationCount = 5)
    public void testIncrementSynchronizedV1() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(threadPool);
        Counter counter = new Counter();
        Runnable r = counter::incrementSynchronizedV1;

        try {
            for (int i = 0; i < invocationCount; i++) {
                service.submit(r);
            }
        } finally {
            service.shutdown();
        }

        service.awaitTermination(10, TimeUnit.SECONDS);
        assertThat(counter.get(), is(equalTo(invocationCount)));
    }

    @Test(invocationCount = 5)
    public void testIncrementSynchronizedV2() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(threadPool);
        Counter counter = new Counter();
        Runnable r = counter::incrementSynchronizedV2;

        try {
            for (int i = 0; i < invocationCount; i++) {
                service.submit(r);
            }
        } finally {
            service.shutdown();
        }

        service.awaitTermination(10, TimeUnit.SECONDS);
        assertThat(counter.get(), is(equalTo(invocationCount)));
    }

    @Test(invocationCount = 5, description = "fail")
    public void testIncrement() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(threadPool);
        Counter counter = new Counter();
        Runnable r = counter::increment;

        try {
            for (int i = 0; i < invocationCount; i++) {
                service.submit(r);
            }
        } finally {
            service.shutdown();
        }

        service.awaitTermination(10, TimeUnit.SECONDS);
        assertThat(counter.get(), is(equalTo(invocationCount)));
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
