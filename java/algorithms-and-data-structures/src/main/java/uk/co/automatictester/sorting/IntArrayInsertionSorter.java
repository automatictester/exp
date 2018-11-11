package uk.co.automatictester.sorting;

public class IntArrayInsertionSorter implements IntArraySorter {

    private int[] array;

    public IntArrayInsertionSorter(int[] array) {
        this.array = array;
    }

    /*
     * Insertion Sort:
     * - O(N2) - worst case - elements sorted in reverse order
     * - O(N2) - average
     * - O(N)  - best case
     */

    public void sort() {
        for (int i = 1; i < array.length; i++) {
            if (!isSorted(i)) {
                int value = array[i];
                int newIndex = getNewIndex(i);
                for (int j = i - 1; j >= newIndex; j--) {
                    array[j + 1] = array[j];
                }
                update(value, newIndex);
            }
        }
    }

    private void update(int value, int index) {
        array[index] = value;
    }

    private int getNewIndex(int oldIndex) {
        for (int i = 0; i < oldIndex; i++) {
            if (i == 0 && array[oldIndex] < array[0]) {
                return 0;
            } else if (array[i] < array[oldIndex] && array[i + 1] > array[oldIndex]) {
                return i + 1;
            }
        }
        throw new RuntimeException("Something is not quite right with the algorithm");
    }

    private boolean isSorted(int i) {
        return array[i - 1] < array[i];
    }
}
