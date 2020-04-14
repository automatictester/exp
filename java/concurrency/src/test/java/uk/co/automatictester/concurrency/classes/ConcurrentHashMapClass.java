package uk.co.automatictester.concurrency.classes;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConcurrentHashMapClass {

    /**
     * This example:
     * - concurrent reads 5x faster
     * - concurrent writes 50% slower
     */

    private final Map<Integer, Integer> synchronizedMap = Collections.synchronizedMap(new HashMap<>());
    private final ConcurrentMap<Integer, Integer> concurrentMap = new ConcurrentHashMap<>();

    @BeforeClass
    public void setup() {
        for (int i = 0; i < 1_000; i++) {
            synchronizedMap.put(i, i * 2);
            concurrentMap.put(i, i * 2);
        }
    }

    @Test(invocationCount = 5)
    public void testWriteSynchronized() throws InterruptedException {
        Map<Integer, Integer> map = Collections.synchronizedMap(new HashMap<>());
        write(map);
    }

    @Test(invocationCount = 5)
    public void testWriteConcurrent() throws InterruptedException {
        ConcurrentMap<Integer, Integer> map = new ConcurrentHashMap<>();
        write(map);
    }

    private void write(Map<Integer, Integer> map) throws InterruptedException {
        Runnable r = () -> {
            for (int i = 0; i < 1_000; i++) {
                map.put(1, 2);
                map.remove(1);
            }
        };
        ExecutorService executor = Executors.newFixedThreadPool(8);
        for (int i = 0; i < 1_000; i++) {
            executor.submit(r);
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        assertThat(map.size(), equalTo(0));

    }

    @Test(invocationCount = 5)
    public void testReadSynchronized() throws InterruptedException {
        read(synchronizedMap);
    }

    @Test(invocationCount = 5)
    public void testReadConcurrent() throws InterruptedException {
        read(concurrentMap);
    }

    private void read(Map<Integer, Integer> map) throws InterruptedException {
        Runnable r = () -> {
            for (int i = 0; i < 1_000; i++) {
                map.get(i);
            }
        };
        ExecutorService executor = Executors.newFixedThreadPool(8);
        for (int i = 0; i < 1_000; i++) {
            executor.submit(r);
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        assertThat(map.size(), equalTo(1_000));
    }
}
