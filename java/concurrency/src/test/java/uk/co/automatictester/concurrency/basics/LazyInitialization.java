package uk.co.automatictester.concurrency.basics;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.concurrent.*;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LazyInitialization {

    private final int threads = 4;
    private final ConcurrentSkipListSet<Singleton> instances = new ConcurrentSkipListSet<>();
    private CountDownLatch latch;

    @BeforeMethod
    public void setup() {
        instances.clear();
    }

    @Test
    public void testLazyInit() throws InterruptedException {
        latch = new CountDownLatch(threads);

        ExecutorService executor = Executors.newFixedThreadPool(threads);

        Runnable r = () -> {
            latch.countDown();
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Singleton instance = Singleton.getInstance();
            instances.add(instance);
        };

        for (int i = 0; i < threads; i++) {
            executor.submit(r);
        }
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        assertThat(instances.size(), not(equalTo(1)));
    }
}

@NotThreadSafe
class Singleton implements Comparable<Singleton> {

    private static Singleton singleton;

    public static Singleton getInstance() {
        if (singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }

    @Override
    public int compareTo(Singleton obj) {
        return this.hashCode() - obj.hashCode();
    }
}
