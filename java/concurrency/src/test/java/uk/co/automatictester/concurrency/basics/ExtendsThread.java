package uk.co.automatictester.concurrency.basics;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

public class ExtendsThread {

    @Test
    public void runThread() throws InterruptedException {
        Thread t = new MyThread();
        t.start();
        t.join();
    }
}

@Slf4j
class MyThread extends Thread {

    public void run() {
        log.info("output");
    }
}
