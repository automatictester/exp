package uk.co.automatictester.concurrency.forkjoin;

import java.util.concurrent.RecursiveTask;

public class ParallelAddRecursiveTask extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 3;
    private final int startInc;
    private final int endExc;
    private final int[] input;


    ParallelAddRecursiveTask(int setStartIndexInclusive, int setEndIndexExclusive, int[] setInput) {
        this.startInc = setStartIndexInclusive;
        this.endExc = setEndIndexExclusive;
        this.input = setInput;
    }

    @Override
    protected Integer compute() {
        int value = 0;
        int elementCount = endExc - startInc;
        if (elementCount <= THRESHOLD) {
            System.out.println("Element count: " + elementCount);
            for (int i = startInc; i < endExc; i++) {
                value += input[i];
            }
            return value;
        } else {
            int middle = (startInc + endExc) / 2;

            ParallelAddRecursiveTask left = new ParallelAddRecursiveTask(startInc, middle, input);
            ParallelAddRecursiveTask right = new ParallelAddRecursiveTask(middle, endExc, input);

            left.fork();
            int leftSum = left.join();
            int rightSum = right.compute();

            return leftSum + rightSum;
        }
    }
}
