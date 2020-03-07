package uk.co.automatictester.concurrency.classes;

import org.testng.annotations.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierClass {

    private Runnable barrierAction = () -> System.out.println("Bareer broken");
    private CyclicBarrier barrier = new CyclicBarrier(2, barrierAction);

    private Thread getThread() {
        Runnable r = () -> {
            System.out.println("Thread starting");
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("Thread finishing");
        };
        return new Thread(r);
    }

    /**
     * Sample output for a CyclicBarrier with 2 parties:
     *
     * Thread starting
     * Thread starting
     * Bareer broken
     * Thread starting
     * Thread finishing
     * Thread finishing
     * Thread starting
     * Bareer broken
     * Thread finishing
     * Thread finishing
     *
     */
    @Test
    public void demoBarrier() throws InterruptedException {
        Thread t1 = getThread();
        Thread t2 = getThread();
        Thread t3 = getThread();
        Thread t4 = getThread();
        t1.start(); t2.start(); t3.start(); t4.start();
        t1.join(); t2.join();; t3.join(); t4.join();
    }
}
