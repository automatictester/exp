package uk.co.automatictester.concurrency.forkjoin;

import java.util.concurrent.RecursiveAction;

public class ParallelAddRecursiveAction extends RecursiveAction {

    private static final int THRESHOLD = 3;
    private final int startInc;
    private final int endExc;
    private final int[] input;
    private int value;


    ParallelAddRecursiveAction(int setStartIndexInclusive, int setEndIndexExclusive, int[] setInput) {
        this.startInc = setStartIndexInclusive;
        this.endExc = setEndIndexExclusive;
        this.input = setInput;
    }

    public int getValue() {
        return value;
    }

    @Override
    protected void compute() {
        int elementCount = endExc - startInc;
        if (elementCount <= THRESHOLD) {
            System.out.println("Element count: " + elementCount);
            for (int i = startInc; i < endExc; i++) {
                value += input[i];
            }
        } else {
            int middle = (startInc + endExc) / 2;

            ParallelAddRecursiveAction left = new ParallelAddRecursiveAction(startInc, middle, input);
            ParallelAddRecursiveAction right = new ParallelAddRecursiveAction(middle, endExc, input);

            invokeAll(left, right);

            value = left.value + right.value;
        }
    }
}
