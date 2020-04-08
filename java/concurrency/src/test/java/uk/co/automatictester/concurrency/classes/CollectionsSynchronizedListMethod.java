package uk.co.automatictester.concurrency.classes;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CollectionsSynchronizedListMethod {

    private final int threads = 10;
    private final int loopCount = 100;
    private List<Integer> list;

    private Runnable r = () -> {
        for (int i = 0; i < loopCount; i++) {
            list.add(1);
            list.remove(0);
        }
    };

    @Test(invocationCount = 50, description = "random fail")
    public void testList() throws InterruptedException {
        list = new ArrayList<>();
        doStuff(r);
        assertThat(list.size(), equalTo(0));
    }

    @Test(invocationCount = 50)
    public void testSynchronizedList() throws InterruptedException {
        list = Collections.synchronizedList(new ArrayList<>());
        doStuff(r);
        assertThat(list.size(), equalTo(0));
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
