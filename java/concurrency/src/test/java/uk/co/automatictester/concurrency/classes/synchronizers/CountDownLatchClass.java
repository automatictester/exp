package uk.co.automatictester.concurrency.classes.synchronizers;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountDownLatchClass {

    private final int threads = 4;

    /*
     * Output:
     * Thread starting
     * Thread starting
     * Thread starting
     * Thread starting
     * Thread finishing
     * Thread finishing
     * Thread finishing
     * Thread finishing
     */
    @Test
    public void demoCountDown() throws InterruptedException {
        MyCountDown myCountDown = new MyCountDown(threads);
        ExecutorService service = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < threads; i++) {
            service.submit(myCountDown.getRunnable());
        }
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
    }
}

@Slf4j
class MyCountDown {

    private final CountDownLatch latch;

    public MyCountDown(int threads) {
        latch = new CountDownLatch(threads);
    }

    public Runnable getRunnable() {
        return () -> {
            log.info("Thread starting");
            latch.countDown();
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("Thread finishing");
        };
    }
}
