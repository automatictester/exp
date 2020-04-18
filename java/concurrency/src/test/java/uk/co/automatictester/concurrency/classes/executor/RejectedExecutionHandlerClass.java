package uk.co.automatictester.concurrency.classes.executor;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class RejectedExecutionHandlerClass {

    @Test(expectedExceptions = RejectedExecutionException.class)
    public void saturationPolicyDefaultAbort() throws InterruptedException {
        Runnable r = () -> log.info("running");
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.shutdown();
        service.submit(r); // throws RejectedExecutionException
        service.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void saturationPolicyDiscard() throws InterruptedException {
        Runnable r = () -> log.info("running");

        ExecutorService service = Executors.newCachedThreadPool();
        RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.DiscardPolicy();
        ((ThreadPoolExecutor) service).setRejectedExecutionHandler(rejectedExecutionHandler);

        service.shutdown();
        service.submit(r); // silently ignore
        service.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test(invocationCount = 5)
    public void saturationPolicyCallerRuns() throws InterruptedException {
        int corePoolSize = 4;
        int maximumPoolSize = 4;
        long keepAliveTime = 1;
        TimeUnit unit = TimeUnit.MINUTES;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(50); // the smaller the bounded queue, the more likely for caller to run instead
        ExecutorService service = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
        ((ThreadPoolExecutor) service).setRejectedExecutionHandler(rejectedExecutionHandler);

        AtomicInteger counter = new AtomicInteger(0);
        Runnable r = () -> {
            if (Thread.currentThread().getName().equals("main")) {
                counter.incrementAndGet();
            }
        };

        int threads = 1_000;
        for (int i = 0; i < threads; i++) {
            service.execute(r);
        }
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
        log.info("Caller ran: {}", counter.get());
    }
}
