package uk.co.automatictester.concurrency.classes.atomic;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Slf4j
public class AtomicClass {

    private final int loopCount = 1_000_000;
    private final int threads = 4;

    @Test
    public void testAtomic() throws InterruptedException {
        Counter counter = new Counter();
        ExecutorService service = Executors.newFixedThreadPool(threads);
        Runnable incr = () -> {
            for (int i = 0; i < loopCount; i++) {
                counter.incrementAndGet();
            }
        };
        Runnable decr = () -> {
            for (int i = 0; i < loopCount; i++) {
                counter.decrementAndGet();
            }
        };
        service.submit(incr);
        service.submit(decr);
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
        assertThat(counter.get(), equalTo(0));
    }
}

class Counter {

    private AtomicInteger a = new AtomicInteger(0);

    public int incrementAndGet() {
        return a.incrementAndGet();
    }

    public int decrementAndGet() {
        return a.decrementAndGet();
    }

    public int get() {
        return a.get();
    }
}
