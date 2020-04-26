package uk.co.automatictester.concurrency.classes.collections;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class CollectionsSynchronizedSetMethod {

    private final int threads = 8;
    private final int loopCount = 1_000;
    private final CyclicBarrier barrier = new CyclicBarrier(threads);
    private Set<Integer> set;

    private final Runnable r = () -> {
        try {
            barrier.await();
        } catch (BrokenBarrierException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < loopCount; i++) {
            int value = ThreadLocalRandom.current().nextInt();
            set.add(value);
        }
    };

    @Test(invocationCount = 5)
    public void testList() throws InterruptedException {
        set = new HashSet<>();
        test(r);
        assertThat(set.size(), not(equalTo(threads * loopCount)));
    }

    @Test
    public void testSynchronizedList() throws InterruptedException {
        set = Collections.synchronizedSet(new HashSet<>());
        test(r);
        assertThat(set.size(), equalTo(threads * loopCount));
    }

    private void test(Runnable r) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < threads; i++) {
            service.submit(r);
        }
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
        int value = ThreadLocalRandom.current().nextInt();
        if (set.contains(value)) {
            log.info("x");
        }
    }
}
