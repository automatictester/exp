package uk.co.automatictester.concurrency.classes;

import org.testng.annotations.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentHashMapClass {

    private static final int THREADS = 1000;
    private static final int ITERATIONS_PER_THREAD = 1000;

    private Map<String, Integer> map = new ConcurrentHashMap<>();

    private ConcurrentHashMapClass() {
        map.put("e", 0);
    }

    private void increment() {
        int e = map.get("e");
        map.replace("e", ++e);
    }

    private synchronized void incrementSynchronized() {
        int e = map.get("e");
        map.replace("e", ++e);
    }

    // will work like increment() without synchronization if applied to plain HashMap
    private void incrementConcurrent() {
        map.computeIfPresent("e", (k, v) -> ++v);
    }

    @Test
    public void incrementMapValue() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(4);
        Runnable r = () -> incrementConcurrent();
        try {
            for (int i = 0; i < THREADS; i++) {
                for (int j = 0; j < ITERATIONS_PER_THREAD; j++) {
                    service.submit(r);
                }
            }
        } finally {
            service.shutdown();
        }
        service.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println(map.get("e"));
    }
}
