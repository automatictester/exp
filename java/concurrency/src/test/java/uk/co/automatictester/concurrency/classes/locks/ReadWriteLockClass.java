package uk.co.automatictester.concurrency.classes.locks;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class ReadWriteLockClass {

    @Test(invocationCount = 5)
    public void testLock() throws InterruptedException {
        LockedValue value = new LockValue();
        test(value);
    }

    @Test(invocationCount = 5)
    public void testReadWriteLock() throws InterruptedException {
        LockedValue value = new ReadWriteLockValue();
        test(value);
    }

    private void test(LockedValue value) throws InterruptedException {
        int threads = 4;
        CyclicBarrier barrier = new CyclicBarrier(threads);

        Runnable runnable = () -> {
            try {
                barrier.await();
            } catch (BrokenBarrierException | InterruptedException e) {
                throw new RuntimeException(e);
            }

            for (int i = 0; i < 5_000; i++) {
                if (i % 20 == 0) {
                    value.increment();
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
        assertThat(value.get(), equalTo(1_000));
    }
}

interface LockedValue {
    void increment();

    int get();
}

class ReadWriteLockValue implements LockedValue {

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private int value = 0;

    @Override
    public void increment() {
        try {
            lock.writeLock().lock();
            this.value++;
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public int get() {
        try {
            lock.readLock().lock();
            return value;
        } finally {
            lock.readLock().unlock();
        }
    }
}

class LockValue implements LockedValue {

    private final Lock lock = new ReentrantLock();
    private int value = 0;

    @Override
    public void increment() {
        try {
            lock.lock();
            this.value++;
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int get() {
        try {
            lock.lock();
            return value;
        } finally {
            lock.unlock();
        }
    }
}
