package uk.co.automatictester.concurrency.forkjoin;

import org.testng.annotations.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class ParallelAddRecursiveTaskTest {

    @Test
    public void test() throws ExecutionException, InterruptedException {
        int[] input = {1, 2, 3, 2, 2, 1, 2, 3, 2, 2};
        ParallelAddRecursiveTask task = new ParallelAddRecursiveTask(0, input.length, input);
        ForkJoinPool.commonPool().invoke(task);
        assert task.get() == 20;
    }
}
