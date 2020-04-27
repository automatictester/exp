package uk.co.automatictester.concurrency.classes.executor;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ThreadPoolExecutorClass {

    @Test
    public void customThreadPoolExecutorConfig() throws InterruptedException {
        Runnable r = () -> log.info("running");

        ExecutorService executor = Executors.newFixedThreadPool(10);
        ((ThreadPoolExecutor) executor).setCorePoolSize(10);
        ((ThreadPoolExecutor) executor).setMaximumPoolSize(10);
        ((ThreadPoolExecutor) executor).setKeepAliveTime(0L, TimeUnit.MILLISECONDS);
        ((ThreadPoolExecutor) executor).setThreadFactory(Thread::new);
        ((ThreadPoolExecutor) executor).setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        ((ThreadPoolExecutor) executor).prestartAllCoreThreads();

        executor.submit(r);
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }
}
