package uk.co.automatictester.sorting;

public class IntArraySelectionSorter implements IntArraySorter {

    private int[] array;
    private int compareCount = 0;
    private int swapCount = 0;

    public IntArraySelectionSorter(int[] array) {
        this.array = array;
    }

    /*
     * Bubble Sort:
     * - O(N2) - worst case - elements sorted in reverse order
     * - O(N2) - average
     * - O(N2) - best case
     */

    public void sort() {

        for (int currentIndex = 0; currentIndex < array.length; currentIndex++) {
            int lowestValueIndex = currentIndex;

            for (int i = currentIndex + 1; i < array.length; i++) {
                if (array[i] < array[lowestValueIndex]) {
                    lowestValueIndex = i;
                }
                compareCount++;
            }

            if (lowestValueIndex > currentIndex) {
                int temp = array[currentIndex];
                array[currentIndex] = array[lowestValueIndex];
                array[lowestValueIndex] = temp;
                swapCount++;
            }
        }

        int totalSteps = compareCount + swapCount;
        System.out.println("Total steps: " + totalSteps);
    }
}