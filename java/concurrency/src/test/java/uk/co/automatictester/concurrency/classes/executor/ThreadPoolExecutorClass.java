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

        ExecutorService service = Executors.newFixedThreadPool(10);
        ((ThreadPoolExecutor) service).setCorePoolSize(10);
        ((ThreadPoolExecutor) service).setMaximumPoolSize(10);
        ((ThreadPoolExecutor) service).setKeepAliveTime(0L, TimeUnit.MILLISECONDS);
        ((ThreadPoolExecutor) service).setThreadFactory(Thread::new);
        ((ThreadPoolExecutor) service).setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

        service.submit(r);
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
    }
}
