package uk.co.automatictester.concurrency.classes;

import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CollectionsSynchronizedSetMethod {

    private final int threads = 10;
    private final int loopCount = 100;
    private Set<Integer> set;

    private Runnable r = () -> {
        for (int i = 0; i < loopCount; i++) {
            set.add(1);
            set.remove(1);
        }
    };

    @Test(invocationCount = 10, description = "random fail")
    public void testList() throws InterruptedException {
        set = new HashSet<>();
        doStuff(r);
        assertThat(set.size(), equalTo(0));
    }

    @Test(invocationCount = 10)
    public void testSynchronizedList() throws InterruptedException {
        set = Collections.synchronizedSet(new HashSet<>());
        doStuff(r);
        assertThat(set.size(), equalTo(0));
    }

    private void doStuff(Runnable r) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < threads; i++) {
            service.submit(r);
        }
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
    }
}
