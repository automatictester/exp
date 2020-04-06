package uk.co.automatictester.concurrency.classes;

import org.testng.annotations.Test;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchClass {

    private CountDownLatch latch = new CountDownLatch(4);

    private Thread getThread() {
        Runnable r = () -> {
            System.out.println("Thread starting");
            latch.countDown();
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread finishing");
        };
        return new Thread(r);
    }

    /**
     * Output for a CountDownLatch with count = 4:
     * Thread starting
     * Thread starting
     * Thread starting
     * Thread starting
     * Thread finishing
     * Thread finishing
     * Thread finishing
     * Thread finishing
     */
    @Test
    public void demoCountDown() throws InterruptedException {
        Thread t1 = getThread();
        Thread t2 = getThread();
        Thread t3 = getThread();
        Thread t4 = getThread();
        t1.start(); t2.start(); t3.start(); t4.start();
        t1.join(); t2.join(); t3.join(); t4.join();
    }
}
