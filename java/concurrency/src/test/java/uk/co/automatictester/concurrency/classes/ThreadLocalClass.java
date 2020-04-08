package uk.co.automatictester.concurrency.classes;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class ThreadLocalClass {

    private final int threads = 100;
    private final ThreadLocal<String> threadLocal = new ThreadLocal<>();
    private final Set<String> threadNames = new ConcurrentSkipListSet<>();

    private final Runnable r = () -> {
        String threadName = Thread.currentThread().getName();
        threadLocal.set(threadName);
        threadNames.add(threadLocal.get());
    };

    @Test(invocationCount = 5)
    public void testThreadLocal() throws InterruptedException {
        threadNames.clear();
        ExecutorService service = Executors.newFixedThreadPool(threads);
        try {
            for (int i = 0; i < threads; i++) {
                service.submit(r);
            }
        } finally {
            service.shutdown();
        }
        service.awaitTermination(10, TimeUnit.SECONDS);
        assertThat(threadNames.size(), equalTo(threads));
    }
}
