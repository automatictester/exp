package uk.co.automatictester.sorting;

public class Context {

    /*
     * Participants:
     * - Context
     * - Strategy (IntArraySortStrategy interface)
     * - Concrete strategy (IntArrayBubbleSortStrategy class)
     */

    public void sortIntArray(int[] array) {

        IntArraySortStrategy strategy = new IntArrayBubbleSortStrategy();
        strategy.sort(array);
    }
}
