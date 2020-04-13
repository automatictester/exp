package uk.co.automatictester.concurrency.basics;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

@Slf4j
public class ImplementsRunnable {

    @Test
    public void runThread() throws InterruptedException {
        // before Java 8
        Thread t1 = new Thread(new MyRunnable());
        t1.start();
        t1.join();

        // Java 8 and later
        Runnable r = () -> log.info("output");
        Thread t2 = new Thread(r);
        t2.start();
        t2.join();
    }
}

@Slf4j
class MyRunnable implements Runnable {

    public void run() {
        log.info("output");
    }
}
