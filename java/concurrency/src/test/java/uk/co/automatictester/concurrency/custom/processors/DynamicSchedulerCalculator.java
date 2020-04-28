package uk.co.automatictester.concurrency.custom.processors;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;

@Slf4j
public class DynamicSchedulerCalculator {

    @Test
    public void assertCorrectness() throws InterruptedException {
        float[] sequential = new SingleThreadedCalculator.Calculator().getValues();
        float sequentialTotal = 0;
        for (int i = 0; i < sequential.length; i++) {
            sequentialTotal += i;
        }

        float[] parallel = new Calculator().getValues();
        float parallelTotal = 0;
        for (int i = 0; i < parallel.length; i++) {
            parallelTotal += i;
        }

        assertThat((double) parallelTotal, closeTo(sequentialTotal, 0.0));
    }

    @Test(invocationCount = 10)
    public void test() throws InterruptedException {
        float[] element = new Calculator().getValues();
        assertThat(element.length, equalTo(36_000_000));
        assertThat((double) element[36_000_000 - 1], closeTo(-3490.48, 0.01));
    }

    @Slf4j
    static class Calculator {

        private final int size = 36_000_000;
        private final int chunkSize = 1_000_000;
        private final float[] lookupValues = new float[size];
        private final Queue<Integer> workQueue = new LinkedBlockingQueue<>();

        public float[] getValues() throws InterruptedException {
            splitWork();
            doWorkInParallel();
            return lookupValues;
        }

        private void splitWork() {
            for (int i = 0; i < size / chunkSize; i++) {
                workQueue.add(i);
            }
        }

        private void doWorkInParallel() throws InterruptedException {
            Runnable doWork = () -> {
                do {
                    Integer chunkNumber = workQueue.poll();
                    if (chunkNumber != null) {
                        int start = chunkNumber * chunkSize;
                        int end = (chunkNumber + 1) * chunkSize;
                        log.debug("Range: {}-{}", start, end);
                        for (int i = start; i < end; i++) {
                            calculateElement(i);
                        }
                    }
                } while (!workQueue.isEmpty());
            };

            ExecutorService executor = Executors.newCachedThreadPool();
            int maxThreads = Runtime.getRuntime().availableProcessors();
            for (int i = 0; i < maxThreads; i++) {
                executor.submit(doWork);
            }
            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.SECONDS);
        }

        private void calculateElement(int i) {
            float sinValue = (float) Math.sin((i % 360) * Math.PI / 180.0);
            lookupValues[i] = sinValue * (float) i / 180.0f;
        }
    }
}
