package uk.co.automatictester.concurrency.real;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Requirements:
 * - alpha cannot execute in parallel with beta
 * - execution order is not important
 */
public class ConcurrentTypeExecution {

    private static final int MAX_EXECUTION_DURATION = 10;
    private static final int THREAD_RETRY_WAIT_TIME = 10;
    private static final int THREAD_COUNT = 1000;

    private ExecutionMutex mutex = new ExecutionMutex();

    private Runnable alpha = () -> {
        while(true) {
            boolean success = mutex.tryIncrementAlpha();
            if (success) {
                int duration = new Random().nextInt(MAX_EXECUTION_DURATION);
                wait(duration);
                mutex.decrementAlpha();
                break;
            }
            wait(THREAD_RETRY_WAIT_TIME);
        }
    };

    private Runnable beta = () -> {
        while(true) {
            boolean success = mutex.tryIncrementBeta();
            if (success) {
                int duration = new Random().nextInt(MAX_EXECUTION_DURATION);
                wait(duration);
                mutex.decrementBeta();
                break;
            }
            wait(THREAD_RETRY_WAIT_TIME);
        }
    };

    private List<Thread> threads = new ArrayList<>();

    /**
     *
     * Sample output:
     *
     * [INFO] ALPHA: phase started
     * [INFO] BETA:  phase started
     * [INFO] ALPHA: phase started
     * [INFO] BETA:  phase started
     * [INFO] ALPHA: phase started
     * [INFO] ALPHA: phase started
     * [INFO] BETA:  phase started
     *
     */
    @Test
    public void runThreads() {
        for (int i = 0; i < THREAD_COUNT; i++) {
            if (i % 2 == 0) {
                threads.add(new Thread(alpha));
            } else {
                threads.add(new Thread(beta));
            }
        }

        threads.forEach(Thread::start);
        threads.forEach(this::join);
    }

    private void wait(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
