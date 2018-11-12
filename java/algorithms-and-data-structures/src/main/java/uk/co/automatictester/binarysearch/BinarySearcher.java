package uk.co.automatictester.binarysearch;

public class BinarySearcher {

    private BinarySearcher() {
    }

    public static int search(int[] array, int value) {
        int lowerBound = 0;
        int upperBound = array.length - 1;

        while (lowerBound <= upperBound) {
            int mid = (upperBound + lowerBound) / 2;
            int midValue = array[mid];
            if (value == midValue) {
                return mid;
            } else if (value < midValue) {
                upperBound = mid - 1;
            } else {
                lowerBound = mid + 1;
            }
        }
        return -1;
    }
}
