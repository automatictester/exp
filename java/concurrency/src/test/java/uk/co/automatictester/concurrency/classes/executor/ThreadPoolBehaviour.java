package uk.co.automatictester.concurrency.classes.executor;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.*;

@Slf4j
public class ThreadPoolBehaviour {

    @DataProvider(name = "getExecutor")
    public Object[][] getExecutor() {
        return new Object[][]{
                {Executors.newFixedThreadPool(200)},
                {Executors.newFixedThreadPool(500)},
                {Executors.newFixedThreadPool(1000)},
                {Executors.newCachedThreadPool()}
        };
    }

    /*
     * Fixed thread pool behaviour on new task submission:
     * - No thread is created until first task is submitted.
     * - If the pool doesn't have target number of threads, it starts a new thread and runs the new task immediately,
     *   even if some of the existing threads are idle.
     * - If the pool has target number of threads and some threads are idle, the task is run by an idle thread.
     * - If the pool has target number of threads and all threads are busy, task is added to the queue.
     *
     * Cached thread pool behaviour on new task submission:
     * - No thread is created until first task is submitted.
     * - If the pool has zero threads, it starts a new thread and runs the new task immediately.
     * - If the pool has some threads and some of the threads are idle, the task is run by an idle thread.
     * - If the pool has some threads and all the threads are busy, it starts a new thread and runs the task immediately.
     *
     * Custom thread pool behaviour on new task submission:
     * - If the pool has created fewer than corePoolSize threads, it starts a new thread and runs the new task
     *   immediately. Even if some of the existing threads are idle, a new thread is created in the pool’s attempt
     *   to reach corePoolSize threads.
     * - If the pool has between corePoolSize and maximumPoolSize threads and one of those threads is idle,
     *   the task is run by an idle thread.
     * - If the pool has between corePoolSize and maximumPoolSize threads and all the threads are busy,
     *   the thread pool examines the existing work queue. If the task can be placed on the work queue
     *   without blocking, it’s put on the queue and no new thread is started.
     * - If the pool has between corePoolSize and maximumPoolSize threads, all threads are busy, and the task
     *   cannot be added to the queue without blocking, the pool starts a new thread and runs the task on that thread.
     * - If the pool has maximumPoolSize threads and all threads are busy, the pool attempts to place the new task
     *   on the queue. If the queue has reached its maximum size, this attempt fails and the task is rejected.
     *   Otherwise, the task is accepted and run when a thread becomes idle (and all previously queued tasks have run).
     */
    @Test(dataProvider = "getExecutor")
    public void observeTotalNumberOfCreatedThreads(ExecutorService executor) throws InterruptedException {
        Runnable r = () -> {
            int x = ThreadLocalRandom.current().nextInt();
            int y = ThreadLocalRandom.current().nextInt();
            if (x == y) {
                log.info("x");
            }
        };

        for (int i = 0; i < 500; i++) {
            executor.execute(r);
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        int maxPoolSize = ((ThreadPoolExecutor) executor).getLargestPoolSize();
        log.info("Threads created: {}", maxPoolSize);
    }
}
