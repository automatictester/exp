package uk.co.automatictester.concurrency.classes.executor;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import javax.annotation.Nonnull;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ThreadFactoryClass {

    @Test
    public void tryCustomThreadStackSize() throws InterruptedException {
        Runnable runnable = () -> log.info(Thread.currentThread().getName());
        ThreadFactory threadFactory = new StackSizeSettingThreadFactory();
        int threads = 1_000;
        ExecutorService service = Executors.newFixedThreadPool(threads, threadFactory);
        for (int i = 0; i < threads; i++) {
            service.execute(runnable);
        }
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
    }
}

class StackSizeSettingThreadFactory implements ThreadFactory {
    private static final AtomicInteger COUNTER = new AtomicInteger(0);

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
