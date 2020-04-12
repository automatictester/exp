package uk.co.automatictester.concurrency.basics;

import org.testng.annotations.Test;

import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class LazyInitialization {

    private final int threads = 1_000;
    private final ConcurrentSkipListSet<Singleton> instances = new ConcurrentSkipListSet<>();

    @Test(invocationCount = 5, description = "random pass/fail")
    public void testLazyInit() throws InterruptedException {
        instances.clear();

        ExecutorService service = Executors.newFixedThreadPool(threads);

        Runnable r = () -> {
            Singleton instance = Singleton.getInstance();
            instances.add(instance);
        };

        for (int i = 0; i < threads; i++) {
            service.submit(r);
        }
        service.shutdown();
        service.awaitTermination(5, TimeUnit.SECONDS);
        assertThat(instances.size(), is(equalTo(1)));
    }
}

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
