package uk.co.automatictester.concurrency.basics;

import org.testng.annotations.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunnableLambdaReinvented {

    private static int c = 0;
    private static final int LIMIT = 1000000;

    private Runnable r = () -> {
        for (int i = 0; i < LIMIT; i++) {
            c++;
        }
    };

    @Test
    public void runThread() throws InterruptedException {
        ExecutorService service = Executors.newSingleThreadExecutor();
        try {
            service.submit(r);
        } finally {
            service.shutdown();
        }


        while (c < LIMIT) {
            System.out.println("waiting... " + c);
            Thread.sleep(1);
        }
        System.out.println("done: " + c);
    }
}
