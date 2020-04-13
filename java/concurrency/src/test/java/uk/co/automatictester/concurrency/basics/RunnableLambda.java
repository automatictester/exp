package uk.co.automatictester.concurrency.basics;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

@Slf4j
public class RunnableLambda {

    private Runnable r = () -> log.info("output");

    @Test
    public void runThread() throws InterruptedException {
        Thread t = new Thread(r);
        t.start();
        t.join();
    }
}
