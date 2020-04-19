package uk.co.automatictester.concurrency.classes.locks;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class LockSemaphoreClass {

    @Test(invocationCount = 5)
    public void testSemaphore() throws InterruptedException {
        ProtectedValue value = new SemaphoreProtectedValue();
        test(value);
    }

    @Test(invocationCount = 5)
    public void testLock() throws InterruptedException {
        ProtectedValue value = new LockProtectedValue();
        test(value);
    }

    private void test(ProtectedValue value) throws InterruptedException {
        int threads = 8;
        CyclicBarrier barrier = new CyclicBarrier(threads);

        Runnable runnable = () -> {
            try {
                barrier.await();
            } catch (BrokenBarrierException | InterruptedException e) {
                throw new RuntimeException(e);
            }

            for (int i = 0; i < 100_000; i++) {
                if (i % 20 == 0) {
                    try {
                        value.increment();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    int x = value.get();
                    if (x == ThreadLocalRandom.current().nextInt()) {
                        log.info("x");
                    }
                }
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < threads; i++) {
            executor.submit(runnable);
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        assertThat(value.get(), equalTo(40_000));
    }
}

interface ProtectedValue {
    void increment() throws InterruptedException;

    int get();
}

@SuppressWarnings("NonAtomicOperationOnVolatileField")
class SemaphoreProtectedValue implements ProtectedValue {

    private final Semaphore semaphore = new Semaphore(1);
    private volatile int value = 0;

    @Override
    public void increment() throws InterruptedException {
        semaphore.acquire();
        this.value++;
        semaphore.release();
    }

    @Override
    public int get() {
        return value;
    }
}

@SuppressWarnings("NonAtomicOperationOnVolatileField")
class LockProtectedValue implements ProtectedValue {

    private final Lock lock = new ReentrantLock();
    private volatile int value = 0;

    @Override
    public void increment() {
        // always lock() before try and unlock() inside finally
        lock.lock();
        try {
            this.value++;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int get() {
        return value;
    }
}
