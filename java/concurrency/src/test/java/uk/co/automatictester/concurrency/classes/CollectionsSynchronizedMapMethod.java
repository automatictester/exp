package uk.co.automatictester.concurrency.classes;

import org.testng.annotations.Test;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CollectionsSynchronizedMapMethod {

    private final int threads = 1_000;
    private final int loopCount = 10;
    private Map<Integer, Integer> map;

    private Runnable r = () -> {
        for (int i = 0; i < loopCount; i++) {
            map.put(1, 1);
            map.remove(1);
        }
    };

    @Test(invocationCount = 50, description = "random fail")
    public void testMap() throws InterruptedException {
        map = new HashMap<>();
        doStuff(r);
        assertThat(map.size(), equalTo(0));
    }

    @Test(invocationCount = 50)
    public void testSynchronizedMap() throws InterruptedException {
        map = Collections.synchronizedMap(new HashMap<>());
        doStuff(r);
        assertThat(map.size(), equalTo(0));
    }

    private void doStuff(Runnable r) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(threads);
        try {
            for (int i = 0; i < threads; i++) {
                service.submit(r);
            }
        } finally {
            service.shutdown();
        }
        service.awaitTermination(10, TimeUnit.SECONDS);
    }
}
