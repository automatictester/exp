package uk.co.automatictester.concurrency.classes;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class ConcurrentHashMapClass {

    private final Map<Integer, Integer> synchronizedMap = Collections.synchronizedMap(new HashMap<>());
    private final ConcurrentMap<Integer, Integer> concurrentMap = new ConcurrentHashMap<>();

    @BeforeClass
    public void setup() {
        for (int i = 0; i < 1_000; i++) {
            int value = ThreadLocalRandom.current().nextInt();
            synchronizedMap.put(i, value);
            concurrentMap.put(i, value);
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
        Runnable runnable = () -> {
            for (int i = 0; i < 1_000; i++) {
                int value = ThreadLocalRandom.current().nextInt();
                map.put(i, value);
            }
        };
        ExecutorService executor = Executors.newFixedThreadPool(8);
        for (int i = 0; i < 1_000; i++) {
            executor.submit(runnable);
        }
        int value = ThreadLocalRandom.current().nextInt();
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        if (map.containsValue(value)) {
            log.info("x");
        }
        assertThat(map.size(), equalTo(1_000));
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
            int x = ThreadLocalRandom.current().nextInt();
            for (int i = 0; i < 1_000; i++) {
                if (map.get(i) == x) {
                    log.info("x");
                }
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
