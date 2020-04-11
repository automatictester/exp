package uk.co.automatictester.concurrency.classes;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CopyOnWriteArrayListTest {

    private final int listSize = 50_000;
    private List<Integer> synchronizedList;

    // a bit faster reads, much slower writes
    private List<Integer> concurrentList = new CopyOnWriteArrayList<>();

    private List<Integer> synchronizedListForWriteOperations = Collections.synchronizedList(new ArrayList<>());
    private List<Integer> concurrentListForWriteOperations = new CopyOnWriteArrayList<>();

    @BeforeClass
    public void setup() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            list.add(i);
            concurrentList.add(i);
        }
        synchronizedList = Collections.synchronizedList(list);
    }

    @Test(invocationCount = 5)
    public void testReadSynchronized() throws InterruptedException {
        Runnable r = () -> {
            for (int i = 0; i < listSize; i++) {
                synchronizedList.get(i);
            }
        };
        run(r);
    }

    @Test(invocationCount = 5)
    public void testReadConcurrent() throws InterruptedException {
        Runnable r = () -> {
            for (int i = 0; i < listSize; i++) {
                concurrentList.get(i);
            }
        };
        run(r);
    }

    @Test(invocationCount = 5)
    public void testWriteSynchronized() throws InterruptedException {
        Runnable r = () -> {
            for (int i = 0; i < listSize; i++) {
                synchronizedListForWriteOperations.add(1);
            }
            for (int i = 0; i < listSize; i++) {
                synchronizedListForWriteOperations.remove(0);
            }
        };
        run(r);
    }

    @Test(invocationCount = 5)
    public void testWriteConcurrent() throws InterruptedException {
        Runnable r = () -> {
            for (int i = 0; i < listSize; i++) {
                concurrentListForWriteOperations.add(i);
            }
            for (int i = 0; i < listSize; i++) {
                concurrentListForWriteOperations.remove(0);
            }
        };
        run(r);
    }

    private void run(Runnable r) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(4);
        try {
            service.submit(r);
        } finally {
            service.shutdown();
        }
        service.awaitTermination(10, TimeUnit.SECONDS);
    }
}
