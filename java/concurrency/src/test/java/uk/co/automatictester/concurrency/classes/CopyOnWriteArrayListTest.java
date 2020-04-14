package uk.co.automatictester.concurrency.classes;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CopyOnWriteArrayListTest {

    /**
     * This example:
     * - concurrent reads over 100x faster
     * - concurrent writes 25% slower
     */

    private final List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());
    private final List<Integer> concurrentList = new CopyOnWriteArrayList<>();

    @BeforeClass
    public void setup() {
        for (int i = 0; i < 50_000; i++) {
            synchronizedList.add(i * 2);
            concurrentList.add(i * 2);
        }
    }

    @Test(invocationCount = 5)
    public void testWriteSynchronized() throws InterruptedException {
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        write(list);
    }

    @Test(invocationCount = 5)
    public void testWriteConcurrent() throws InterruptedException {
        List<Integer> list = new CopyOnWriteArrayList<>();
        write(list);
    }

    private void write(List<Integer> list) throws InterruptedException {
        Runnable r = () -> {
            for (int i = 0; i < 20_000; i++) {
                list.add(i * 2);
                list.remove(0);
            }
        };
        ExecutorService executor = Executors.newFixedThreadPool(8);
        for (int i = 0; i < 1_000; i++) {
            executor.submit(r);
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        assertThat(list.size(), equalTo(0));
    }

    @Test(invocationCount = 5)
    public void testReadSynchronized() throws InterruptedException {
        read(synchronizedList);
    }

    @Test(invocationCount = 5)
    public void testReadConcurrent() throws InterruptedException {
        read(concurrentList);
    }

    private void read(List<Integer> list) throws InterruptedException {
        Runnable r = () -> {
            for (int i = 0; i < 50_000; i++) {
                list.get(i);
            }
        };
        ExecutorService executor = Executors.newFixedThreadPool(8);
        for (int i = 0; i < 1_000; i++) {
            executor.submit(r);
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        assertThat(list.size(), equalTo(50_000));
    }
}
