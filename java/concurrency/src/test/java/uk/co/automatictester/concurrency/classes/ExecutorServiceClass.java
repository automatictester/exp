package uk.co.automatictester.concurrency.classes;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ExecutorServiceClass {

    @Test
    public void execute() throws InterruptedException {
        Runnable r = () -> log.info("running");
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(r); // like submit(), but void (doesn't return Future)
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
        log.info("last statement");
    }

    @Test
    public void submit() throws InterruptedException {
        Runnable r = () -> log.info("running");
        ExecutorService service = Executors.newCachedThreadPool();
        service.submit(r);
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void submitAndWaitToFinish() throws ExecutionException, InterruptedException {
        Runnable r = () -> log.info("running");
        ExecutorService service = Executors.newCachedThreadPool();
        Future<?> future = service.submit(r);
        future.get(); // blocks until submit() finished execution; will return null as r is Runnable not Callable
        log.info("statement after future.get()");
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
    }


    @Test
    public void submitAndWaitForResult() throws ExecutionException, InterruptedException {

        Callable<Integer> c = () -> {
            log.info("running");
            return 2;
        };

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

        Runnable r = () -> log.info(Thread.currentThread().getName());

        ThreadGroup threadGroup = null;
        long suggestedAndMostLikelyIgnoredThreadStackSize = 64 * 1024; // 64k
        AtomicInteger threadCounter = new AtomicInteger(0);

        ThreadFactory threadFactory = threadFactoryRunnable -> {
            String threadName = String.valueOf(threadCounter.incrementAndGet());
            return new Thread(
                    threadGroup,
                    threadFactoryRunnable,
                    threadName,
                    suggestedAndMostLikelyIgnoredThreadStackSize
            );
        };

        int threads = 1_000;
        ExecutorService service = Executors.newFixedThreadPool(threads, threadFactory);
        for (int i = 0; i < threads; i++) {
            service.execute(r);
        }
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
    }

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

    @Test(expectedExceptions = RejectedExecutionException.class)
    public void saturationPolicyDefaultAbort() throws InterruptedException {
        Runnable r = () -> log.info("running");
        ExecutorService service = Executors.newCachedThreadPool();
        service.shutdown();
        service.submit(r); // throws RejectedExecutionException
        service.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void saturationPolicyDiscard() throws InterruptedException {
        Runnable r = () -> log.info("running");

        ExecutorService service = Executors.newCachedThreadPool();
        ((ThreadPoolExecutor) service).setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());

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
        ((ThreadPoolExecutor) service).setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

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
