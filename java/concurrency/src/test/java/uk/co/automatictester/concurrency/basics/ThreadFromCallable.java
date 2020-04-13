package uk.co.automatictester.concurrency.basics;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class ThreadFromCallable {

    @Test
    public void runThread() throws ExecutionException, InterruptedException {
        Callable<Integer> c = () -> {
            Thread.sleep(1000);
            log.info("output");
            return 1;
        };

        FutureTask<Integer> ft = new FutureTask<>(c);
        new Thread(ft).start();
        int i = ft.get();
        assertThat(i, equalTo(1));
    }
}
