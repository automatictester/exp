package uk.co.automatictester.concurrency.classes;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class CollectionsSynchronizedMapMethod {

    private final int threads = 8;
    private final int loopCount = 1_000;
    private Map<Integer, Integer> map;

    private Runnable r = () -> {
        for (int i = 0; i < loopCount; i++) {
            int key = ThreadLocalRandom.current().nextInt();
            int value = ThreadLocalRandom.current().nextInt();
            map.put(key, value);
        }
    };

    @Test(invocationCount = 5)
    public void testMap() throws InterruptedException {
        map = new HashMap<>();
        test(r);
        assertThat(map.size(), not(equalTo(8_000)));
    }

    @Test
    public void testSynchronizedMap() throws InterruptedException {
        map = Collections.synchronizedMap(new HashMap<>());
        test(r);
        assertThat(map.size(), equalTo(8_000));
    }

    private void test(Runnable r) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < threads; i++) {
            service.submit(r);
        }
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
        int value = ThreadLocalRandom.current().nextInt();
        if (map.containsKey(value)) {
            log.info("x");
        }
    }
}
