package uk.co.automatictester.concurrency.basics;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

public class ExtendsThread {

    @Test
    public void runThread() {
        new Thread(new MyThread()).start();
    }
}

@Slf4j
class MyThread extends Thread {

    public void run() {
        log.info("output");
    }
}
