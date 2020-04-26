package uk.co.automatictester.concurrency.custom;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CompositeCounterTest {

    protected final int threads = 16;
    protected CountDownLatch latch;
    protected CompositeCounter mutex;
    private final int taskExecutionTime = 10;

    protected Runnable alpha = () -> {
        try {
            latch.countDown();
            latch.await();
            mutex.incrementAlpha();
            Thread.sleep(taskExecutionTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        mutex.decrementAlpha();
    };

    protected Runnable beta = () -> {
        try {
            latch.countDown();
            latch.await();
            mutex.incrementBeta();
            Thread.sleep(taskExecutionTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        mutex.decrementBeta();
    };

    public void doTrialMutex() throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 1_000; i++) {
            if (i % 2 == 0) {
                executor.submit(alpha);
            } else {
                executor.submit(beta);
            }
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }
}
