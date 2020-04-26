package uk.co.automatictester.concurrency.classes.executor;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@Slf4j
public class RejectedExecutionHandlerClass {

    @Test(expectedExceptions = RejectedExecutionException.class)
    public void saturationPolicyDefaultAbort() throws InterruptedException {
        RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.AbortPolicy();
        submitAfterShutdown(rejectedExecutionHandler); // throws RejectedExecutionException
    }

    @Test
    public void saturationPolicyDiscard() throws InterruptedException {
        RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.DiscardPolicy();
        submitAfterShutdown(rejectedExecutionHandler); // silently ignore
    }

    @Test
    public void saturationPolicyCallerRuns() throws InterruptedException {
        RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
        submitAfterShutdown(rejectedExecutionHandler); // silently ignore (!)
    }

    private void submitAfterShutdown(RejectedExecutionHandler rejectedExecutionHandler) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        ((ThreadPoolExecutor) executor).setRejectedExecutionHandler(rejectedExecutionHandler);

        Runnable r = () -> log.info("running");

        executor.shutdown();
        executor.submit(r);
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test(expectedExceptions = RejectedExecutionException.class)
    public void saturationPolicyDefaultAbortQueueFull() throws InterruptedException {
        RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.AbortPolicy();
        submitQueueFull(rejectedExecutionHandler); // throws RejectedExecutionException
    }

    @Test
    public void saturationPolicyDiscardQueueFull() throws InterruptedException {
        RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.DiscardPolicy();
        submitQueueFull(rejectedExecutionHandler); // silently ignore
    }

    private void submitQueueFull(RejectedExecutionHandler rejectedExecutionHandler) throws InterruptedException {
        int corePoolSize = 2;
        int maximumPoolSize = 2;
        long keepAliveTime = 1;
        TimeUnit unit = TimeUnit.MINUTES;
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(5);

        ExecutorService executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        ((ThreadPoolExecutor) executor).setRejectedExecutionHandler(rejectedExecutionHandler);

        Runnable r = () -> log.info("running");

        for (int i = 0; i < 100; i++) {
            executor.submit(r);
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void saturationPolicyCallerRunsQueueFull() throws InterruptedException {
        int corePoolSize = 2;
        int maximumPoolSize = 2;
        long keepAliveTime = 1;
        TimeUnit unit = TimeUnit.MINUTES;
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(5); // the smaller the bounded queue, the more likely for caller to run instead

        ExecutorService executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();

        ((ThreadPoolExecutor) executor).setRejectedExecutionHandler(rejectedExecutionHandler);

        AtomicInteger counter = new AtomicInteger();
        Runnable r = () -> {
            if (Thread.currentThread().getName().equals("main")) {
                counter.incrementAndGet();
            }
        };

        for (int i = 0; i < 100; i++) {
            executor.submit(r);
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        assertThat(counter.get(), greaterThan(0));
    }
}
