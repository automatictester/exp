package uk.co.automatictester.concurrency.classes.executor;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
public class ExecutorServiceClass {

    @Test
    public void execute() throws InterruptedException {
        Runnable r = () -> log.info("running");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(r); // like submit(), but void (doesn't return Future)
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        log.info("last statement");
    }

    @Test
    public void submit() throws InterruptedException {
        Runnable r = () -> log.info("running");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(r);
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void submitAndWaitToFinish() throws ExecutionException, InterruptedException {
        Runnable r = () -> log.info("running");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(r);
        future.get(); // blocks until submit() finished execution; will return null as r is Runnable not Callable
        log.info("statement after future.get()");
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void submitAndWaitForResult() throws ExecutionException, InterruptedException {
        Callable<Integer> c = () -> 2;
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(c);
        int i = future.get();
        log.info("Value: {}", i);
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void invokeAll() throws InterruptedException {
        List<Callable<Integer>> callables = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int v = i;
            Callable<Integer> c = () -> v;
            callables.add(c);
        }

        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<Integer>> results = executor.invokeAll(callables);
        results.forEach(r -> {
            try {
                int result = r.get();
                log.info("{}", result);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }
}
