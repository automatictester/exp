package uk.co.automatictester.concurrency.classes.executor;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.annotation.Nonnull;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ThreadFactoryClass {

    @Test
    public void tryCustomThreadStackSize() throws InterruptedException {
        Runnable runnable = () -> log.info(Thread.currentThread().getName());
        ThreadFactory threadFactory = StackSizeSettingThreadFactory.getInstance();
        int threads = 1_000;
        ExecutorService service = Executors.newFixedThreadPool(threads, threadFactory);
        for (int i = 0; i < threads; i++) {
            service.execute(runnable);
        }
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
    }

    @DataProvider(name = "getExecutor")
    public Object[][] getExecutor() {
        return new Object[][]{
                {Executors.newFixedThreadPool(200)},
                {Executors.newFixedThreadPool(500)},
                {Executors.newFixedThreadPool(1000)},
                {Executors.newCachedThreadPool()}
        };
    }

    @Test(dataProvider = "getExecutor")
    public void observeTotalNumberOfCreatedThreads(ExecutorService executor) throws InterruptedException {
        Runnable r = () -> {
            int x = ThreadLocalRandom.current().nextInt();
            int y = ThreadLocalRandom.current().nextInt();
            if (x == y) {
                log.info("x");
            }
        };

        for (int i = 0; i < 500; i++) {
            executor.execute(r);
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        int maxPoolSize = ((ThreadPoolExecutor) executor).getLargestPoolSize();
        log.info("Threads created: {}", maxPoolSize);
    }
}

class StackSizeSettingThreadFactory implements ThreadFactory {
    private static final StackSizeSettingThreadFactory INSTANCE = new StackSizeSettingThreadFactory();
    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    private StackSizeSettingThreadFactory() {
    }

    public static StackSizeSettingThreadFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public Thread newThread(@Nonnull Runnable runnable) {
        String threadName = String.valueOf(COUNTER.incrementAndGet());

        // default maximum thread stack size: 1024k
        //
        // this override is most likely going to be ignored
        // other option would be to specify VM option instead: -Xss256k
        // it sets the maximum stack size for threads
        // if your goal is to reduce thread stack size hoping to increase maximum number of threads,
        // you should first check ulimit -u
        long suggestedAndMostLikelyIgnoredThreadStackSize = 256 * 1024; // 256k

        return new Thread(
                null,
                runnable,
                threadName,
                suggestedAndMostLikelyIgnoredThreadStackSize
        );
    }
}
