package uk.co.automatictester.concurrency.classes;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeMethod;
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
    private final String initialValue = "NOT_SET";
    private final ThreadLocal<String> threadLocal = ThreadLocal.withInitial(() -> initialValue);
    private final Set<String> threadNames = new ConcurrentSkipListSet<>();

    private final Runnable r = () -> {
        String threadName = Thread.currentThread().getName();
        threadLocal.set(threadName);
        threadNames.add(threadLocal.get());
    };

    @BeforeMethod
    public void setup() {
        threadNames.clear();
    }

    @Test(invocationCount = 5)
    public void testThreadLocal() throws InterruptedException {
        assertThat(threadLocal.get(), equalTo(initialValue));

        ExecutorService service = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < threads; i++) {
            service.submit(r);
        }
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);

        assertThat(threadNames.size(), equalTo(threads));

        threadLocal.remove();
        assertThat(threadLocal.get(), equalTo(initialValue));
    }
}
