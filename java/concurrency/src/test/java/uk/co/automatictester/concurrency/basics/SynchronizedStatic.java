package uk.co.automatictester.concurrency.basics;

import org.testng.annotations.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class SynchronizedStatic {

    protected final int threads = 16;
    private final int loopCount = 1_000;
    protected CountDownLatch latch;

    @Test
    public void test() throws InterruptedException {
        latch = new CountDownLatch(threads);

        Runnable r = () -> {
            try {
                latch.countDown();
                latch.await();
                for (int i = 0; i < loopCount; i++) {
                    MySynchronizedStatic.incrementA();
                    MySynchronizedStatic.incrementB();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < threads; i++) {
            executor.submit(r);
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        assertThat(MySynchronizedStatic.getA(), equalTo(threads * loopCount));
        assertThat(MySynchronizedStatic.getB(), equalTo(threads * loopCount));
    }
}

class MySynchronizedStatic {

    private volatile static int a;
    private volatile static int b;

    public synchronized static void incrementA() {
        a++;
    }

    public static void incrementB() {
        synchronized (MySynchronizedStatic.class) {
            b++;
        }
    }

    public static int getA() {
        return a;
    }

    public static int getB() {
        return b;
    }
}
