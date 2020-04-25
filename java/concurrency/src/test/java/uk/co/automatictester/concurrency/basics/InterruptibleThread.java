package uk.co.automatictester.concurrency.basics;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class InterruptibleThread {

    @Test
    public void interruptLongRunningThreadRunnable() throws Exception {

        Runnable r = () -> {
            while (!Thread.currentThread().isInterrupted()) {
            }
            // Runnable cannot throw exceptions
            log.info("Thread interrupted");
        };

        Thread t = new Thread(r);
        t.start();

        Thread.sleep(1); // let the thread start
        t.interrupt();

        t.join();
    }

    @Test
    public void interruptLongRunningThreadCallable() throws Exception {

        Callable<Integer> c = () -> {
            while (!Thread.currentThread().isInterrupted()) {
            }
            throw new InterruptedException();
        };

        FutureTask<Integer> ft = new FutureTask<>(c);
        Thread t = new Thread(ft);
        t.start();

        Thread.sleep(1); // let the thread start
        t.interrupt();

        try {
            ft.get(); // t.join() won't do the job with FutureTask
        } catch (ExecutionException e) {
            log.info("{}", e.getCause().toString());
            assertThat(e.getCause().getClass(), equalTo(InterruptedException.class));
        }
    }
}
