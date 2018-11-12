package uk.co.automatictester.sorting;

public class IntArraySelectionSorter implements IntArraySorter {

    private int[] array;

    public IntArraySelectionSorter(int[] array) {
        this.array = array;
    }

    public void sort() {

        for (int i = 0; i < array.length; i++) {
            int lowestValueIndex = i;

            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[lowestValueIndex]) {
                    lowestValueIndex = j;
                }
            }

            if (i < lowestValueIndex) {
                swap(i, lowestValueIndex);
            }
        }
    }

    private void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;

    }
}
