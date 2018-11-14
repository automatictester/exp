package uk.co.automatictester.arraysintersection;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ArrayIntersection {

    private ArrayIntersection() {
    }

    public static List<Integer> find(int[] a, int[] b) {
        List<Integer> intersection = new LinkedList<>();
        Arrays.sort(a);
        Arrays.sort(b);

        int aIndex = 0;
        int bIndex = 0;

        while (aIndex < a.length && bIndex < b.length) {
            if (a[aIndex] == b[bIndex]) {
                intersection.add(a[aIndex]);
                aIndex++;
                bIndex++;
            } else if (a[aIndex] < b[bIndex]) {
                aIndex++;
            } else if (a[aIndex] > b[bIndex]) {
                bIndex++;
            }
        }

        return intersection;
    }
}
