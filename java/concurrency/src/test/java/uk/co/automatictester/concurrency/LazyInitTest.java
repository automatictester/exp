package uk.co.automatictester.concurrency;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@Slf4j
public class LazyInitTest {

    private static final int THREAD_COUNT = 1_000;
    private ConcurrentSkipListSet<LazyInit> instances = new ConcurrentSkipListSet<>();

    @Test(description = "random pass/fail")
    public void testLazyInit() throws InterruptedException {
        instances.clear();

        ExecutorService service = Executors.newFixedThreadPool(THREAD_COUNT);

        Runnable incr = () -> {
            LazyInit instance = LazyInit.getInstance();
            instances.add(instance);
        };

        try {
            for (int i = 0; i < THREAD_COUNT; i++) {
                service.submit(incr);
            }
        } finally {
            service.shutdown();
        }

        service.awaitTermination(5, TimeUnit.SECONDS);

        log.info("Instance count: {}", instances.size());
        assertThat(instances.size(), is(equalTo(1)));
    }
}

class LazyInit implements Comparable<LazyInit> {

    private static LazyInit lazyInit;

    public static LazyInit getInstance() {
        if (lazyInit == null) {
            lazyInit = new LazyInit();
        }
        return lazyInit;
    }

    @Override
    public int compareTo(LazyInit obj) {
        return this.hashCode() - obj.hashCode();
    }
}
