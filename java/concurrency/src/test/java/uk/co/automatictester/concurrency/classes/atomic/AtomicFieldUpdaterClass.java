package uk.co.automatictester.concurrency.classes.atomic;

import org.testng.annotations.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AtomicFieldUpdaterClass {

    public volatile int v;
    public volatile int b;

    // all updates to volatile field through the same updater are guaranteed to be atomic
    private static final AtomicIntegerFieldUpdater<AtomicFieldUpdaterClass> updater =
            AtomicIntegerFieldUpdater.newUpdater(AtomicFieldUpdaterClass.class, "v");

    @Test
    public void test() throws InterruptedException {
        int loopCount = 10_000;
        int threads = 16;
        CountDownLatch latch = new CountDownLatch(threads);

        Runnable r = () -> {
            try {
                latch.countDown();
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (int i = 0; i < loopCount; i++) {
                updater.incrementAndGet(this);
                b++;
            }
        };

        ExecutorService service = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < threads; i++) {
            service.submit(r);
        }
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);

        assertThat(v, equalTo(threads * loopCount));
        assertThat(b, not(equalTo(threads * loopCount)));
    }
}
