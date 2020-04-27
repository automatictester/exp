package uk.co.automatictester.concurrency.classes.executor;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ScheduledThreadPoolExecutorClass {

    @Test
    public void scheduleAtFixedRate() throws InterruptedException {
        Runnable r = () -> {
            log.info("{}", LocalTime.now());
            try {
                Thread.sleep(1200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(4);
        // higher of execution time and period between start of n-1 and start of n
        executor.scheduleAtFixedRate(r, 0, 1, TimeUnit.SECONDS);
        Thread.sleep(6000);
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void scheduleWithFixedDelay() throws InterruptedException {
        Runnable r = () -> {
            log.info("{}", LocalTime.now());
            try {
                Thread.sleep(1200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(4);
        // 1s between end of n-1 and start of n; start-to-start = execution time + delay
        executor.scheduleWithFixedDelay(r, 0, 1, TimeUnit.SECONDS);
        Thread.sleep(10_000);
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }
}
