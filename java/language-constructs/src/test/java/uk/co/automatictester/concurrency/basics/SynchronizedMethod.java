package uk.co.automatictester.concurrency.basics;

import org.testng.annotations.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class SynchronizedMethod {

    private static final int LIMIT = 1_000_000;

    private static int i = 0;

    private synchronized void increment() {
        i++;
    }

    private void incrementEquivalent() {
        synchronized (this) {
            i++;
        }
    }

    private void incrementNotSynchronized() {
        i++;
    }

    @Test
    public void testSynchronization() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(4);
        Runnable r = () -> increment();
        try {
            for (int i = 0; i < LIMIT; i++) {
                service.submit(r);
            }
        } finally {
            service.shutdown();
        }
        service.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println(i);
        assertThat(i, is(equalTo(LIMIT)));
    }
}
