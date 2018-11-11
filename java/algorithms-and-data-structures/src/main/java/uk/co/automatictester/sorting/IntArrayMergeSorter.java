package uk.co.automatictester.sorting;

import java.util.LinkedList;
import java.util.Queue;

public class IntArrayMergeSorter implements IntArraySorter {

    private int[] array;

    public IntArrayMergeSorter(int[] array) {
        this.array = array;
    }

    public void sort() {
        Queue<Queue<Integer>> elements = new LinkedList<>();

        for (int i : array) {
            Queue<Integer> q = new LinkedList<>();
            q.add(i);
            elements.add(q);
        }

        while (elements.size() > 1) {
            Queue<Queue<Integer>> merged = new LinkedList<>();
            int count = elements.size();
            for (int i = 0; i < count; i += 2) {
                merged.add(merge(elements.poll(), elements.poll()));
            }
            elements = merged;
        }

        for (int i = 0; i < array.length; i++) {
            array[i] = elements.peek().poll();
        }
    }

    static Queue<Integer> merge(Queue<Integer> q1, Queue<Integer> q2) {
        Queue<Integer> merged = new LinkedList<>();

        if (q2 == null) return q1;

        while (!q1.isEmpty() && !q2.isEmpty()) {
            if (q1.peek() < q2.peek()) {
                merged.add(q1.poll());
            } else {
                merged.add(q2.poll());
            }
        }

        while (!q1.isEmpty()) {
            merged.add(q1.poll());
        }
        while (!q2.isEmpty()) {
            merged.add(q2.poll());
        }

        return merged;
    }
}
