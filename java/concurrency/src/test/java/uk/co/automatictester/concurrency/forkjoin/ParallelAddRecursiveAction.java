package uk.co.automatictester.concurrency.forkjoin;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RecursiveAction;

@Slf4j
public class ParallelAddRecursiveAction extends RecursiveAction {

    private final int threshold;
    private final int startInc;
    private final int endExc;
    private final int[] input;
    private int value;

    ParallelAddRecursiveAction(int setStartIndexInclusive, int setEndIndexExclusive, int[] setInput, int threshold) {
        this.startInc = setStartIndexInclusive;
        this.endExc = setEndIndexExclusive;
        this.input = setInput;
        this.threshold = threshold;
    }

    public int getValue() {
        return value;
    }

    @Override
    protected void compute() {
        int elementCount = endExc - startInc;
        if (elementCount <= threshold) {
            log.info("Element count: {}", elementCount);
            for (int i = startInc; i < endExc; i++) {
                value += input[i];
            }
        } else {
            int middle = (startInc + endExc) / 2;

            ParallelAddRecursiveAction left = new ParallelAddRecursiveAction(startInc, middle, input, threshold);
            ParallelAddRecursiveAction right = new ParallelAddRecursiveAction(middle, endExc, input, threshold);

            invokeAll(left, right);

            value = left.value + right.value;
        }
    }
}
