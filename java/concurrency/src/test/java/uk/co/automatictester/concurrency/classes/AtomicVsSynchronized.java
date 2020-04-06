package uk.co.automatictester.concurrency.classes;

import org.testng.annotations.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class AtomicVsSynchronized {

    /*
     * First, it is not possible for two invocations of synchronized methods on the same object to interleave.
     * When one thread is executing a synchronized method for an object, all other threads that invoke
     * synchronized methods for the same object block (suspend execution) until the first thread is done with the
     * object.
     *
     * Second, when a synchronized method exits, it automatically establishes a happens-before relationship
     * with any subsequent invocation of a synchronized method for the same object. This guarantees that changes
     * to the state of the object are visible to all threads.
     */

    private static final int THREAD_COUNT = 4;
    private static final int LIMIT = 10_000_000;

    private AtomicInteger i = new AtomicInteger(0);
    private int j = 0;
    private int k = 0;

    private void increment() {
        i.incrementAndGet();
    }

    private void decrement() {
        i.decrementAndGet();
    }

    private synchronized void synchronisedIncrement() {
        j++;
    }

    private synchronized void synchronizedDecrement() {
        j--;
    }

    private void notThreadSafeIncrement() {
        k++;
    }

    private void notThreadSafeDecrement() {
        k--;
    }

    @Test(invocationCount = 5)
    public void testAtomic() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(THREAD_COUNT);
        Runnable incr = () -> {
            for (int i = 0; i < LIMIT; i++) {
                increment();
            }
        };
        Runnable decr = () -> {
            for (int i = 0; i < LIMIT; i++) {
                decrement();
            }
        };
        try {
            for (int i = 0; i < THREAD_COUNT; i++) {
                service.submit(incr);
                service.submit(decr);
            }
        } finally {
            service.shutdown();
        }
        service.awaitTermination(20, TimeUnit.SECONDS);
        System.out.println(i);
        assertThat(i.get(), is(equalTo(0)));
    }

    @Test(invocationCount = 5)
    public void testSynchronized() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(THREAD_COUNT);
        Runnable incr = () -> {
            for (int i = 0; i < LIMIT; i++) {
                synchronisedIncrement();
            }
        };
        Runnable decr = () -> {
            for (int i = 0; i < LIMIT; i++) {
                synchronizedDecrement();
            }
        };
        try {
            for (int i = 0; i < THREAD_COUNT; i++) {
                service.submit(incr);
                service.submit(decr);
            }
        } finally {
            service.shutdown();
        }
        service.awaitTermination(20, TimeUnit.SECONDS);
        System.out.println(j);
        assertThat(j, is(equalTo(0)));
    }

    @Test(description = "fail")
    public void testNotThreadSafe() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(THREAD_COUNT);
        Runnable incr = () -> {
            for (int i = 0; i < LIMIT; i++) {
                notThreadSafeIncrement();
            }
        };
        Runnable decr = () -> {
            for (int i = 0; i < LIMIT; i++) {
                notThreadSafeDecrement();
            }
        };
        try {
            for (int i = 0; i < THREAD_COUNT; i++) {
                service.submit(incr);
                service.submit(decr);
            }
        } finally {
            service.shutdown();
        }
        service.awaitTermination(20, TimeUnit.SECONDS);
        System.out.println(k);
        assertThat(k, is(equalTo(0)));
    }
}
