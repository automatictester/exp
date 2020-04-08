package uk.co.automatictester.concurrency.forkjoin;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RecursiveTask;

@Slf4j
public class ParallelAddRecursiveTask extends RecursiveTask<Integer> {

    private final int threshold;
    private final int startInc;
    private final int endExc;
    private final int[] input;

    ParallelAddRecursiveTask(int setStartIndexInclusive, int setEndIndexExclusive, int[] setInput, int threshold) {
        this.startInc = setStartIndexInclusive;
        this.endExc = setEndIndexExclusive;
        this.input = setInput;
        this.threshold = threshold;
    }

    @Override
    protected Integer compute() {
        int value = 0;
        int elementCount = endExc - startInc;
        if (elementCount <= threshold) {
            log.info("Element count: {}", elementCount);
            for (int i = startInc; i < endExc; i++) {
                value += input[i];
            }
            return value;
        } else {
            int middle = (startInc + endExc) / 2;

            ParallelAddRecursiveTask left = new ParallelAddRecursiveTask(startInc, middle, input, threshold);
            ParallelAddRecursiveTask right = new ParallelAddRecursiveTask(middle, endExc, input, threshold);

            left.fork();
            int leftSum = left.join();
            int rightSum = right.compute();

            return leftSum + rightSum;
        }
    }
}
