package uk.co.automatictester.concurrency.classes.collections;

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
    private final int listSize = 100_000;
    private final int threads = 8;

    @BeforeClass
    public void setup() {
        for (int i = 0; i < listSize; i++) {
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
        CyclicBarrier barrier = new CyclicBarrier(threads);
        Runnable runnable = () -> {
            try {
                barrier.await();
            } catch (BrokenBarrierException | InterruptedException e) {
                throw new RuntimeException(e);
            }

            for (int i = 0; i < listSize; i++) {
                int value = ThreadLocalRandom.current().nextInt();
                map.put(i, value);
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < threads; i++) {
            executor.submit(runnable);
        }
        int value = ThreadLocalRandom.current().nextInt();
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        if (map.containsValue(value)) {
            log.info("x");
        }
        assertThat(map.size(), equalTo(listSize));
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
        CyclicBarrier barrier = new CyclicBarrier(threads);
        Runnable runnable = () -> {
            try {
                barrier.await();
            } catch (BrokenBarrierException | InterruptedException e) {
                throw new RuntimeException(e);
            }

            int x = ThreadLocalRandom.current().nextInt();
            for (int i = 0; i < listSize; i++) {
                if (map.get(i) == x) {
                    log.info("x");
                }
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < threads; i++) {
            executor.submit(runnable);
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        assertThat(map.size(), equalTo(listSize));
    }
}
