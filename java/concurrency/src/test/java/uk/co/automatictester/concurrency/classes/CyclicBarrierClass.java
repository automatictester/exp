package uk.co.automatictester.concurrency.classes;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.concurrent.*;

public class CyclicBarrierClass {

    private final int threads = 4;

    /*
     * Sample output for a CyclicBarrier with 2 parties:
     * Thread starting
     * Thread starting
     * Bareer broken
     * Thread starting
     * Thread finishing
     * Thread finishing
     * Thread starting
     * Bareer broken
     * Thread finishing
     * Thread finishing
     */
    @Test
    public void demoBarrier() throws InterruptedException {
        MyCyclicBarrier myCyclicBarrier = new MyCyclicBarrier();
        ExecutorService service = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < threads; i++) {
            service.submit(myCyclicBarrier.getRunnable());
        }
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
    }
}

@Slf4j
class MyCyclicBarrier {

    private Runnable barrierAction = () -> log.info("Bareer broken");
    private CyclicBarrier barrier = new CyclicBarrier(2, barrierAction);

    public Runnable getRunnable() {
        return () -> {
            log.info("Thread starting");
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            log.info("Thread finishing");
        };
    }
}
