package uk.co.automatictester.concurrency.classes;

import org.testng.annotations.Test;

import java.util.concurrent.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConcurrentHashMapClass {

    private final int loopCount = 1_000;
    private final int threads = 4;

    @Test(description = "slower than 'replaceSynchronized()'")
    public void testComputeIfPresent() throws InterruptedException {
        MyConcurrentMap myConcurrentMap = new MyConcurrentMap();
        ExecutorService service = Executors.newFixedThreadPool(threads);
        Runnable r = myConcurrentMap::computeIfPresent;
        for (int i = 0; i < loopCount; i++) {
            service.submit(r);
        }
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
        assertThat(myConcurrentMap.get("e"), equalTo(loopCount));
    }

    @Test
    public void testReplaceSynchronized() throws InterruptedException {
        MyConcurrentMap myConcurrentMap = new MyConcurrentMap();
        ExecutorService service = Executors.newFixedThreadPool(threads);
        Runnable r = myConcurrentMap::replaceSynchronized;
        for (int i = 0; i < loopCount; i++) {
            service.submit(r);
        }
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
        assertThat(myConcurrentMap.get("e"), equalTo(loopCount));
    }

    @Test(description = "fail")
    public void testReplace() throws InterruptedException {
        MyConcurrentMap myConcurrentMap = new MyConcurrentMap();
        ExecutorService service = Executors.newFixedThreadPool(threads);
        Runnable r = myConcurrentMap::replace;
        for (int i = 0; i < loopCount; i++) {
            service.submit(r);
        }
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
        assertThat(myConcurrentMap.get("e"), equalTo(loopCount));
    }
}

class MyConcurrentMap {

    private ConcurrentMap<String, Integer> map = new ConcurrentHashMap<>();

    public MyConcurrentMap() {
        map.put("e", 0);
    }

    public void computeIfPresent() {
        map.computeIfPresent("e", (k, v) -> ++v);
    }

    public synchronized void replaceSynchronized() {
        int e = map.get("e");
        map.replace("e", ++e);
    }

    public void replace() {
        int e = map.get("e");
        map.replace("e", ++e);
    }

    public int get(String key) {
        return map.get(key);
    }
}
