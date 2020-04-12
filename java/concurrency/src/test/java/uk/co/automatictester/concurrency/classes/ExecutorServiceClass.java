package uk.co.automatictester.concurrency.classes;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.concurrent.*;

@Slf4j
public class ExecutorServiceClass {

    private final Runnable r = () -> log.info("running");

    private final Callable<Integer> c = () -> {
        log.info("running");
        return 2;
    };

    @Test
    public void execute() throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(r); // like submit(), but void (doesn't return Future)
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
        log.info("last statement");
    }

    @Test
    public void submit() throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        service.submit(r);
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void submitAndWaitToFinish() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        Future<?> future = service.submit(r);
        future.get(); // blocks until submit() finished execution; will return null as r is Runnable not Callable
        log.info("statement after future.get()");
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
    }


    @Test
    public void submitAndWaitForResult() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        Future<Integer> future = service.submit(c);
        int i = future.get();
        log.info("Value: {}", i);
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
    }
}
