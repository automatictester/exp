package uk.co.automatictester.concurrency.classes;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
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

    @Test
    public void runtimeExceptionExecuteRunnable() throws InterruptedException {
        Runnable r = () -> {
            // Runnable cannot throw checked exception
            throw new RuntimeException();
        };

        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(r); // will handle (log) exception and continue
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void runtimeExceptionExecuteRunnableCustomHandler() throws InterruptedException {
        Runnable r = () -> {
            // Runnable cannot throw checked exception
            throw new RuntimeException();
        };

        // overrides default exception handler (see above)
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = (t, e) -> {
            log.error("Exception in thread '" + t.getName() + "': " + e);
        };

        ThreadFactory threadFactory = threadFactoryRunnable -> {
            Thread t = new Thread(threadFactoryRunnable);
            t.setUncaughtExceptionHandler(uncaughtExceptionHandler);
            return t;
        };

        ExecutorService service = Executors.newSingleThreadExecutor(threadFactory);
        service.execute(r);
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test(expectedExceptions = ExecutionException.class)
    public void runtimeExceptionSubmitRunnable() throws InterruptedException, ExecutionException {
        Runnable r = () -> {
            // Runnable cannot throw checked exception
            throw new RuntimeException();
        };

        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<?> f = service.submit(r); // won't throw anything
        f.get(); // throws: java.util.concurrent.ExecutionException: java.lang.RuntimeException
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test(expectedExceptions = CancellationException.class)
    public void taskCancellationThroughFuture() throws InterruptedException, ExecutionException {
        Callable<Integer> c = () -> {
            Thread.sleep(1000);
            return 0;
        };

        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<?> f = service.submit(c);
        f.cancel(true);
        f.get(); // throws CancellationException
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void taskCancellationAfterGetTimeout() throws InterruptedException, ExecutionException {
        Callable<Integer> c = () -> {
            Thread.sleep(1000);
            return 0;
        };

        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<?> f = service.submit(c);
        try {
            f.get(100, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            log.error(e.toString());
            f.cancel(true);
        }
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void tryCustomThreadStackSize() throws InterruptedException {

        ThreadGroup threadGroup = null;
        long suggestedThreadStackSize = 64 * 1024; // 64k
        String threadName = RandomStringUtils.randomAlphanumeric(8); // probably not the best idea ever

        ThreadFactory threadFactory = threadFactoryRunnable -> new Thread(
                threadGroup,
                threadFactoryRunnable,
                threadName,
                suggestedThreadStackSize // platform-dependent and likely to be ignored
        );

        int threads = 1_000;
        ExecutorService service = Executors.newFixedThreadPool(threads, threadFactory);
        for (int i = 0; i < threads; i++) {
            service.execute(r);
        }
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
    }
}
