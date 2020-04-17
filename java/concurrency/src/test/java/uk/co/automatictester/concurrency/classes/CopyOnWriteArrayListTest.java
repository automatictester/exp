package uk.co.automatictester.concurrency.classes;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class CopyOnWriteArrayListTest {

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
        Runnable runnable = () -> {
            for (int i = 0; i < 1_000; i++) {
                int x = ThreadLocalRandom.current().nextInt();
                list.add(x);
            }
            int y = ThreadLocalRandom.current().nextInt();
            if (list.contains(y)) {
                log.info("x");
            }
            list.clear();
        };
        ExecutorService executor = Executors.newFixedThreadPool(8);
        for (int i = 0; i < 1_000; i++) {
            executor.submit(runnable);
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
                int x = list.get(i);
                int y = ThreadLocalRandom.current().nextInt();
                if (x == y) {
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
        assertThat(list.size(), equalTo(50_000));
    }
}
