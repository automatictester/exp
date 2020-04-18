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
        List<PriorityRunnable> runnables = getRunnables(runnableCount);
        execute(runnables);
        assertThat(priorities.size(), equalTo(runnableCount));
        calculateStats();
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
        log.info("Percent: {}%", String.format("%.1f", percent));
    }

    private void execute(List<PriorityRunnable> runnables) throws InterruptedException {
        int cpuCount = Runtime.getRuntime().availableProcessors();
        ExecutorService service = getPriorityQueueBasedThreadPoolExecutor(cpuCount);
        // service.submit() throws ClassCastException: java.util.concurrent.FutureTask cannot be cast to java.lang.Comparable
        runnables.parallelStream().forEach(service::execute);
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
    }

    private ExecutorService getPriorityQueueBasedThreadPoolExecutor(int threads) {
        int corePoolSize = threads;
        int maximumPoolSize = threads;
        long keepAliveTime = 0;
        TimeUnit unit = TimeUnit.MINUTES;
        BlockingQueue<Runnable> workQueue = new PriorityBlockingQueue<>();
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    private List<PriorityRunnable> getRunnables(int count) {
        List<PriorityRunnable> runnables = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            if (i % 2 == 0) {
                runnables.add(new PriorityRunnable(1, priorities));
            } else {
                runnables.add(new PriorityRunnable(2, priorities));
            }
        }
        return runnables;
    }
}

class PriorityRunnable implements Runnable, Comparable<PriorityRunnable> {
    private final int priority;
    private final List<Integer> priorities;

    public PriorityRunnable(int priority, List<Integer> priorities) {
        this.priority = priority;
        this.priorities = priorities;
    }

    @Override
    public void run() {
        priorities.add(priority);
    }

    @Override
    public int compareTo(PriorityRunnable o) {
        return this.priority - o.priority;
    }
}
