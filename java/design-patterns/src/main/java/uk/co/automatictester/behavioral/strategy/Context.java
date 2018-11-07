package uk.co.automatictester.behavioral.strategy;

public class Context {

    public void sortIntArray(int[] array) {

        IntArraySortStrategy strategy = new IntArrayBubbleSortStrategy();
        strategy.sort(array);
    }
}
