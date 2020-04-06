package uk.co.automatictester.concurrency.classes;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Phaser;

public class PhaserClass {

    private Phaser ph1 = new Phaser();
    private Phaser ph2 = new Phaser();
    private Phaser ph3 = new Phaser();

    private int ph1phaseNum;
    private int ph2phaseNum;
    private int ph3phaseNum;

    private Runnable r1 = () -> {
        ph1.register();
        wait(9);
        System.out.println("1");
        ph1phaseNum = ph1.arrive();
    };

    private Runnable r2 = () -> {
        ph2.register();
        wait(10);
        System.out.println("2");
        ph2phaseNum = ph2.arrive();
    };

    private Runnable r3 = () -> {
        ph3.register();
        System.out.println("3");
        ph3phaseNum = ph3.arrive();
    };

    private Runnable r4 = () -> {
        ph2.awaitAdvance(ph2phaseNum);
        System.out.println("4 after 2");
    };

    private Runnable r5 = () -> {
        ph1.awaitAdvance(ph1phaseNum);
        ph3.awaitAdvance(ph3phaseNum);
        System.out.println("5 after 1, 3");
    };

    private Runnable r6 = () -> {
        ph3.awaitAdvance(ph3phaseNum);
        System.out.println("6 after 3");
    };

    private Thread t1 = new Thread(r1);
    private Thread t2 = new Thread(r2);
    private Thread t3 = new Thread(r3);
    private Thread t4 = new Thread(r4);
    private Thread t5 = new Thread(r5);
    private Thread t6 = new Thread(r6);

    private List<Thread> threads = new ArrayList<>();

    /**
     * Sample output:
     *
     * 3
     * 1
     * 2
     * 6 after 3
     * 4 after 2
     * 5 after 1, 3
     *
     */
    @Test
    public void runThread() {
        threads.add(t1);
        threads.add(t2);
        threads.add(t3);
        threads.add(t4);
        threads.add(t5);
        threads.add(t6);

        threads.forEach(Thread::start);
        threads.forEach(this::join);
    }

    private void wait(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    private void join(Thread t) {
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
