package uk.co.automatictester.concurrency.basics;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

@Slf4j
public class InterruptibleThread {

    @Test
    public void interruptLongRunningThreadRunnable() throws Exception {

        Runnable r = () -> {
            for (int i = 0; i < 1_000_000; i++) {
                if (Thread.currentThread().isInterrupted()) {
                    log.info("Thread interrupted at iteration: {}", i);
                    return;
                }
            }
            log.info("All done!"); // never executed due to t.interrupt()
        };

        Thread t = new Thread(r);
        t.start();

        Thread.sleep(1); // let the thread start
        t.interrupt();

        t.join();
    }
}
