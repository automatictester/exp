package uk.co.automatictester.concurrency.real;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * Requirements:
 * - alpha cannot execute in parallel with beta
 * - execution order is not important
 */
public class ConcurrentCompositeCounterTest {

    private final int maxExecutionDuration = 10;
    private final int threadRetryWaitTime = 10;
    private final int threads = 1_000;

    private ConcurrentCompositeCounter mutex;

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
        mutex = new ConcurrentCompositeCounter();
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

/*
 * Improved version of SynchronizedCompositeCounter:
 * - decrement operations are not synchronised and thus always allowed
 * - logging will include estimates (conscious trade-off)
 */
@Slf4j
class ConcurrentCompositeCounter {

    private AtomicInteger alphaCount = new AtomicInteger(0);
    private AtomicInteger betaCount = new AtomicInteger(0);
    private Semaphore incrementSynchronizer = new Semaphore(1);
    private AtomicInteger highestThreadCount = new AtomicInteger(0);

    public boolean tryIncrementAlpha() {
        try {
            incrementSynchronizer.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (betaCount.get() == 0) {
            if (alphaCount.get() == 0) {
                log.debug("ALPHA: phase started");
            }
            alphaCount.incrementAndGet();
            if (alphaCount.get() > highestThreadCount.get()) {
                highestThreadCount.set(alphaCount.get());
            }
            log.debug("ALPHA: {} - BETA: {}", alphaCount.get(), betaCount.get());
            incrementSynchronizer.release();
            return true;
        } else {
            incrementSynchronizer.release();
            return false;
        }
    }

    public boolean tryIncrementBeta() {
        try {
            incrementSynchronizer.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (alphaCount.get() == 0) {
            if (betaCount.get() == 0) {
                log.debug("BETA:  phase started");
            }
            betaCount.incrementAndGet();
            if (betaCount.get() > highestThreadCount.get()) {
                highestThreadCount.set(betaCount.get());
            }
            log.debug("ALPHA: {} - BETA: {}", alphaCount.get(), betaCount.get());
            incrementSynchronizer.release();
            return true;
        } else {
            incrementSynchronizer.release();
            return false;
        }
    }

    public void decrementAlpha() {
        alphaCount.decrementAndGet();
    }

    public void decrementBeta() {
        betaCount.decrementAndGet();
    }

    public void logHighestThreadCount() {
        log.info("Highest thread count: {}", highestThreadCount.get());
    }
}
