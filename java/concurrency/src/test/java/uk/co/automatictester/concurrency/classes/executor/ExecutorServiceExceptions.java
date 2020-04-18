package uk.co.automatictester.concurrency.classes.executor;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.concurrent.*;

@Slf4j
public class ExecutorServiceExceptions {

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
}
