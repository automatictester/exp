package uk.co.automatictester.concurrency.classes;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class PhaserClass {

    /**
     * Sample output:
     * 3
     * 1
     * 2
     * 6 after 3
     * 4 after 2
     * 5 after 1, 3
     */
    @Test
    public void runThread() throws InterruptedException {
        MyPhaser myPhaser = new MyPhaser();
        ExecutorService service = Executors.newFixedThreadPool(myPhaser.getRunnables().size());
        try {
            myPhaser.getRunnables().forEach(service::submit);
        } finally {
            service.shutdown();
        }
        service.awaitTermination(10, TimeUnit.SECONDS);
    }
}

@Slf4j
class MyPhaser {

    private List<Runnable> runnables = new ArrayList<>();

    private Phaser phaser1 = new Phaser();
    private Phaser phaser2 = new Phaser();
    private Phaser phaser3 = new Phaser();

    private int phaser1Id;
    private int phaser2Id;
    private int phaser3Id;

    public MyPhaser() {
        runnables.add(r1);
        runnables.add(r2);
        runnables.add(r3);
        runnables.add(r4);
        runnables.add(r5);
        runnables.add(r6);
    }

    private Runnable r1 = () -> {
        phaser1.register();
        log.info("1");
        phaser1Id = phaser1.arrive();
    };

    private Runnable r2 = () -> {
        phaser2.register();
        log.info("2");
        phaser2Id = phaser2.arrive();
    };

    private Runnable r3 = () -> {
        phaser3.register();
        log.info("3");
        phaser3Id = phaser3.arrive();
    };

    private Runnable r4 = () -> {
        phaser2.awaitAdvance(phaser2Id);
        log.info("4 after 2");
    };

    private Runnable r5 = () -> {
        phaser1.awaitAdvance(phaser1Id);
        phaser3.awaitAdvance(phaser3Id);
        log.info("5 after 1, 3");
    };

    private Runnable r6 = () -> {
        phaser3.awaitAdvance(phaser3Id);
        log.info("6 after 3");
    };

    public List<Runnable> getRunnables() {
        return runnables;
    }
}
