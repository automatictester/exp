package uk.co.automatictester.concurrency.classes.executor;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.concurrent.*;

public class PriorityThreadPoolExecutor {

    @Test
    public void priorityQueue() throws InterruptedException {
        int runnableCount = 100;

        ExecutorService executor = getPriorityQueueBasedThreadPoolExecutor();
        for (int i = 0; i < runnableCount; i++) {
            Runnable r;
            if (i % 2 == 0) {
                r = new PriorityRunnable(1);
            } else {
                r = new PriorityRunnable(2);
            }
            // executor.submit() throws ClassCastException: java.util.concurrent.FutureTask cannot be cast to java.lang.Comparable
            executor.execute(r);
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    private ExecutorService getPriorityQueueBasedThreadPoolExecutor() {
        int corePoolSize = 0;
        int maximumPoolSize = Integer.MAX_VALUE;
        long keepAliveTime = 60;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new PriorityBlockingQueue<>();
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }
}

@Slf4j
class PriorityRunnable implements Runnable, Comparable<PriorityRunnable> {
    private final int priority;

    public PriorityRunnable(int priority) {
        this.priority = priority;
    }

    @Override
    public void run() {
        log.info("Priority: {}", priority);
    }

    @Override
    public int compareTo(PriorityRunnable o) {
        return this.priority - o.priority;
    }
}
