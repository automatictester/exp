package uk.co.automatictester.concurrency.custom.processors;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;

@Slf4j
public class DynamicSchedulerCalculator {

    private static final int ELEMENT_COUNT = 36_000_000;

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
        assertThat(parallel, equalTo(sequential));
    }

    @Test(invocationCount = 10)
    public void test() throws InterruptedException {
        float[] result = new Calculator().getValues();
        assertThat(result.length, equalTo(ELEMENT_COUNT));
        assertThat((double) result[ELEMENT_COUNT - 1], closeTo(-3490.48, 0.01));
    }

    @Slf4j
    static class Calculator {

        private final int size = ELEMENT_COUNT;
        private final int chunkSize = 1_000_000;
        private final float[] lookupValues = new float[size];
        private final Queue<Integer> workQueue = new ConcurrentLinkedQueue<>();

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

        private final Runnable doWork = () -> {
            while (true) {
                Integer chunkNumber = workQueue.poll();
                if (chunkNumber != null) {
                    int start = chunkNumber * chunkSize;
                    int end = (chunkNumber + 1) * chunkSize;
                    log.debug("Range: {}-{}", start, end);
                    for (int i = start; i < end; i++) {
                        calculateElement(i);
                    }
                } else {
                    break;
                }
            }
        };

        private void doWorkInParallel() throws InterruptedException {
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
