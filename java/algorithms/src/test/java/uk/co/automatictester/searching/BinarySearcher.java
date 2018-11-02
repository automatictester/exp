package uk.co.automatictester.searching;

public class BinarySearcher {

    private BinarySearcher() {
    }

    public static int search(int[] array, int value) {
        int iterations = 0;

        int lowerBound = 0;
        int upperBound = array.length - 1;

        while (lowerBound <= upperBound) {
            int mid = (upperBound + lowerBound) / 2;
            int midValue = array[mid];
            if (value == midValue) {
                System.out.println("Iterations: " + iterations);
                return mid;
            } else if (value < midValue) {
                upperBound = mid - 1;
            } else {
                lowerBound = mid + 1;
            }
            iterations++;
        }

        System.out.println("Iterations: " + iterations);
        return -1;
    }
}
