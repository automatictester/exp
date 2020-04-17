package uk.co.automatictester.concurrency.real;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/*
 * Requirements:
 * - alpha cannot execute in parallel with beta
 * - execution order is not important
 */
public class SynchronizedCompositeCounterTest {

    private final int maxExecutionDuration = 10;
    private final int threadRetryWaitTime = 10;
    private final int threads = 1_000;

    private SynchronizedCompositeCounter mutex;

    private Runnable alpha = () -> {
        while (true) {
            boolean success = mutex.tryIncrementAlpha();
            if (success) {
                int duration = ThreadLocalRandom.current().nextInt(maxExecutionDuration);
                wait(duration);
                mutex.decrementAlpha();
                break;
            }
            wait(threadRetryWaitTime);
        }
    };

    private Runnable beta = () -> {
        while (true) {
            boolean success = mutex.tryIncrementBeta();
            if (success) {
                int duration = ThreadLocalRandom.current().nextInt(maxExecutionDuration);
                wait(duration);
                mutex.decrementBeta();
                break;
            }
            wait(threadRetryWaitTime);
        }
    };

    private List<Runnable> runnables = new ArrayList<>();

    /*
     * Sample output:
     * [INFO] ALPHA: phase started
     * [INFO] BETA:  phase started
     * [INFO] ALPHA: phase started
     * [INFO] BETA:  phase started
     * [INFO] ALPHA: phase started
     * [INFO] ALPHA: phase started
     * [INFO] BETA:  phase started
     */
    @Test(invocationCount = 5)
    public void trialMutex() throws InterruptedException {
        mutex = new SynchronizedCompositeCounter();
        generateRunnables();
        ExecutorService service = Executors.newFixedThreadPool(threads);
        runnables.forEach(service::submit);
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
        mutex.logHighestThreadCount();
    }

    private void generateRunnables() {
        for (int i = 0; i < threads; i++) {
            if (i % 2 == 0) {
                runnables.add(new Thread(alpha));
            } else {
                runnables.add(new Thread(beta));
            }
        }
    }

    private void wait(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

@Slf4j
class SynchronizedCompositeCounter {

    private int alphaCount = 0;
    private int betaCount = 0;
    private int highestThreadCount = 0;

    public synchronized boolean tryIncrementAlpha() {
        if (betaCount == 0) {
            if (alphaCount == 0) {
                log.debug("ALPHA: phase started");
            }
            alphaCount++;
            if (alphaCount > highestThreadCount) {
                highestThreadCount = alphaCount;
            }
            log.debug("ALPHA: {} - BETA: {}", alphaCount, betaCount);
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean tryIncrementBeta() {
        if (alphaCount == 0) {
            if (betaCount == 0) {
                log.debug("BETA:  phase started");
            }
            betaCount++;
            if (betaCount > highestThreadCount) {
                highestThreadCount = betaCount;
            }
            log.debug("ALPHA: {} - BETA: {}", alphaCount, betaCount);
            return true;
        } else {
            return false;
        }
    }

    public synchronized void decrementAlpha() {
        alphaCount--;
    }

    public synchronized void decrementBeta() {
        betaCount--;
    }

    public synchronized void logHighestThreadCount() {
        log.info("Highest thread count: {}", highestThreadCount);
    }
}