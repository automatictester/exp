package uk.co.automatictester.sorting;

public class IntArrayBubbleSorter implements IntArraySorter {

    private int[] array;

    public IntArrayBubbleSorter(int[] array) {
        this.array = array;
    }

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
    }

    private boolean compare(int i) {
        return array[i] > array[i + 1];
    }

    private void swap(int i) {
        int temp = array[i];
        array[i] = array[i + 1];
        array[i + 1] = temp;
    }
}
