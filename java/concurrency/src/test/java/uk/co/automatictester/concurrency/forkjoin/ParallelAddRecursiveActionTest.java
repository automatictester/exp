package uk.co.automatictester.concurrency.forkjoin;

import org.testng.annotations.Test;

import java.util.concurrent.ForkJoinPool;

public class ParallelAddRecursiveActionTest {

    @Test
    public void test() {
        int[] input = {1, 2, 3, 2, 2, 1, 2, 3, 2, 2};
        ParallelAddRecursiveAction task = new ParallelAddRecursiveAction(0, input.length, input, 4);
        ForkJoinPool.commonPool().invoke(task);
        assert task.getValue() == 20;
    }
}
