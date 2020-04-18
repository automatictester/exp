package uk.co.automatictester.concurrency.basics;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LazyInitialization {

    private final int threads = 4;
    private final ConcurrentSkipListSet<Singleton> instances = new ConcurrentSkipListSet<>();

    @BeforeMethod
    public void setup() {
        instances.clear();
    }

    @Test(invocationCount = 5, description = "random pass/fail")
    public void testLazyInit() throws InterruptedException {
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
        assertThat(instances.size(), equalTo(1));
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
