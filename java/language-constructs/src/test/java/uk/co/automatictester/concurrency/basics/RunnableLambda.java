package uk.co.automatictester.concurrency.basics;

import org.testng.annotations.Test;

public class RunnableLambda {

    private static int c = 0;
    private static final int LIMIT = 1000000;

    @Test
    public void runThread() throws InterruptedException {
        Runnable r = () -> {
            for (int i = 0; i < LIMIT; i++) {
                c++;
            }
        };
        new Thread(r).start();


        while (c < LIMIT) {
            System.out.println("waiting... " + c);
            Thread.sleep(1);
        }
        System.out.println("done: " + c);
    }
}
