package uk.co.automatictester.concurrency.classes;

import org.testng.annotations.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class AtomicClasses {

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

    private final int loopCount = 1_000_000;
    private final int threads = 4;

    @Test(invocationCount = 5)
    public void testAtomic() throws InterruptedException {
        Counter counter = new Counter();
        ExecutorService service = Executors.newFixedThreadPool(threads);
        Runnable incr = () -> {
            for (int i = 0; i < loopCount; i++) {
                counter.incrementAtomic();
            }
        };
        Runnable decr = () -> {
            for (int i = 0; i < loopCount; i++) {
                counter.decrementAtomic();
            }
        };
        try {
            service.submit(incr);
            service.submit(decr);
        } finally {
            service.shutdown();
        }
        service.awaitTermination(10, TimeUnit.SECONDS);
        assertThat(counter.getAtomic(), is(equalTo(0)));
    }
}

class Counter {

    private AtomicInteger a = new AtomicInteger(0);

    public int incrementAtomic() {
        return a.incrementAndGet();
    }

    public int decrementAtomic() {
        return a.decrementAndGet();
    }

    public int getAtomic() {
        return a.get();
    }
}
