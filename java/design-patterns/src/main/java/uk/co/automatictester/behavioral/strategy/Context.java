package uk.co.automatictester.behavioral.strategy;

public class Context {

    /*
     * Participants:
     * - Context (this class)
     * - Strategy (IntArraySortStrategy interface)
     * - Concrete strategy (IntArrayBubbleSortStrategy class)
     */

    public void sortIntArray(int[] array) {

        IntArraySortStrategy strategy = new IntArrayBubbleSortStrategy();
        strategy.sort(array);
    }
}
