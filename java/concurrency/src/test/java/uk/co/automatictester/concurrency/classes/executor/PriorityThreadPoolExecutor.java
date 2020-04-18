package uk.co.automatictester.concurrency.classes.executor;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@SuppressWarnings("UnnecessaryLocalVariable")
@Slf4j
public class PriorityThreadPoolExecutor {

    private final List<Integer> priorities = Collections.synchronizedList(new ArrayList<>());

    @BeforeMethod
    public void clear() {
        priorities.clear();
    }

    @Test(invocationCount = 5)
    public void priorityQueue() throws InterruptedException {
        int runnableCount = 100_000;
        int threads = 4;
        CyclicBarrier barrier = new CyclicBarrier(threads);

        List<PriorityRunnable> runnables = new ArrayList<>();
        for (int i = 0; i < runnableCount; i++) {
            if (i % 2 == 0) {
                runnables.add(new PriorityRunnable(1, priorities, barrier));
            } else {
                runnables.add(new PriorityRunnable(2, priorities, barrier));
            }
        }

        ExecutorService service = getPriorityQueueBasedThreadPoolExecutor(threads);
        for (int i = 0; i < runnableCount; i++) {
            // service.submit() throws ClassCastException: java.util.concurrent.FutureTask cannot be cast to java.lang.Comparable
            service.execute(runnables.get(i));
        }
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);

        assertThat(priorities.size(), equalTo(runnableCount));
        calculateStats();
    }

    private ExecutorService getPriorityQueueBasedThreadPoolExecutor(int threads) {
        int corePoolSize = threads;
        int maximumPoolSize = threads;
        long keepAliveTime = 0;
        TimeUnit unit = TimeUnit.MINUTES;
        BlockingQueue<Runnable> workQueue = new PriorityBlockingQueue<>();
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    private void calculateStats() {
        double topPriorityInFirstHalf = 0;
        int firstHalf = priorities.size() / 2;
        for (int i = 0; i < firstHalf; i++) {
            if (priorities.get(i) == 1)
                topPriorityInFirstHalf++;
        }
        double halfOfRunnables = (double) priorities.size() / 2;
        double percent = (topPriorityInFirstHalf / halfOfRunnables) * 100;
        log.info("Percent: {}%", String.format("%.4f", percent));
    }
}

class PriorityRunnable implements Runnable, Comparable<PriorityRunnable> {
    private final int priority;
    private final List<Integer> priorities;
    private final CyclicBarrier barrier;

    public PriorityRunnable(int priority, List<Integer> priorities, CyclicBarrier barrier) {
        this.priority = priority;
        this.priorities = priorities;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            barrier.await();
        } catch (BrokenBarrierException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        priorities.add(priority);
    }

    @Override
    public int compareTo(PriorityRunnable o) {
        return this.priority - o.priority;
    }
}
