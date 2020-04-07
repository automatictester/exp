package uk.co.automatictester.concurrency.basics;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

public class ImplementsRunnable {

    @Test
    public void runThread() {
        new Thread(new MyRunnable()).start();
    }
}

@Slf4j
class MyRunnable implements Runnable {

    public void run() {
        log.info("output");
    }
}
