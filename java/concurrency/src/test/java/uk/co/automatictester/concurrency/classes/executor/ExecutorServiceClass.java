package uk.co.automatictester.concurrency.classes.executor;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.concurrent.*;

@Slf4j
public class ExecutorServiceClass {

    @Test
    public void execute() throws InterruptedException {
        Runnable r = () -> log.info("running");
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(r); // like submit(), but void (doesn't return Future)
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
        log.info("last statement");
    }

    @Test
    public void submit() throws InterruptedException {
        Runnable r = () -> log.info("running");
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.submit(r);
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void submitAndWaitToFinish() throws ExecutionException, InterruptedException {
        Runnable r = () -> log.info("running");
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<?> future = service.submit(r);
        future.get(); // blocks until submit() finished execution; will return null as r is Runnable not Callable
        log.info("statement after future.get()");
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void submitAndWaitForResult() throws ExecutionException, InterruptedException {
        Callable<Integer> c = () -> 2;
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<Integer> future = service.submit(c);
        int i = future.get();
        log.info("Value: {}", i);
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
    }
}
