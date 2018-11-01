package uk.co.automatictester.sorting;

public class BubbleIntArraySorter implements IntArraySorter {

    private int[] array;
    private int compareCount = 0;
    private int swapCount = 0;

    public BubbleIntArraySorter(int[] array) {
        this.array = array;
    }

    /*
     * Bubble Sort:
     * - O(N2) - worst case
     * - O(N2) - average
     * - O(N)  - best case
     */

    public void sort() {
        boolean hasChanged;
        int elementsToInspect = array.length;
        do {
            hasChanged = false;
            for (int i = 0; i < elementsToInspect - 1; i++) {
                if (compare(i)) {
                    swap(i);
                    hasChanged = true;
                }
            }
            elementsToInspect--;
        } while (hasChanged);

        int totalSteps = compareCount + swapCount;
        System.out.println("Total iterations: " + totalSteps);
    }

    private boolean compare(int i) {
        compareCount++;
        return array[i] > array[i + 1];
    }

    private void swap(int i) {
        int temp = array[i];
        array[i] = array[i + 1];
        array[i + 1] = temp;
        swapCount++;
    }
}
