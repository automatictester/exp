package uk.co.automatictester.concurrency.basics;

import org.testng.annotations.Test;

public class ExtendsThread extends Thread {

    private String output = "output";

    public void run() {
        System.out.println(output);
    }

    @Test
    public void runThread() {
        new Thread(new ExtendsThread()).start();
    }
}
