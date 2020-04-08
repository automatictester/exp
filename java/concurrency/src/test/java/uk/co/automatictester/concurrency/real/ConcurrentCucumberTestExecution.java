package uk.co.automatictester.concurrency.real;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Requirements:
 * - alpha cannot execute in parallel with beta
 * - execution order is not important
 */
public class ConcurrentCucumberTestExecution {

    private final int maxExecutionDuration = 10;
    private final int threadRetryWaitTime = 10;
    private final int threads = 1_000;

    private ExecutionMutex mutex = new ExecutionMutex();

    private Runnable alpha = () -> {
        while (true) {
            boolean success = mutex.tryIncrementAlpha();
            if (success) {
                int duration = new Random().nextInt(maxExecutionDuration);
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
                int duration = new Random().nextInt(maxExecutionDuration);
                wait(duration);
                mutex.decrementBeta();
                break;
            }
            wait(threadRetryWaitTime);
        }
    };

    private List<Runnable> runnables = new ArrayList<>();

    /**
     * Sample output:
     * [INFO] ALPHA: phase started
     * [INFO] BETA:  phase started
     * [INFO] ALPHA: phase started
     * [INFO] BETA:  phase started
     * [INFO] ALPHA: phase started
     * [INFO] ALPHA: phase started
     * [INFO] BETA:  phase started
     */
    @Test
    public void trialMutex() throws InterruptedException {
        generateRunnables();
        ExecutorService service = Executors.newFixedThreadPool(threads);
        try {
            runnables.forEach(service::submit);
        } finally {
            service.shutdown();
        }
        service.awaitTermination(10, TimeUnit.SECONDS);
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
class ExecutionMutex {

    private int alphaCount = 0;
    private int betaCount = 0;

    public synchronized boolean tryIncrementAlpha() {
        if (betaCount == 0) {
            if (alphaCount == 0) {
                log.info("ALPHA: phase started");
            }
            alphaCount++;
            log.info("ALPHA: {} - BETA: {}", alphaCount, betaCount);
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean tryIncrementBeta() {
        if (alphaCount == 0) {
            if (betaCount == 0) {
                log.info("BETA:  phase started");
            }
            betaCount++;
            log.info("ALPHA: {} - BETA: {}", alphaCount, betaCount);
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
}