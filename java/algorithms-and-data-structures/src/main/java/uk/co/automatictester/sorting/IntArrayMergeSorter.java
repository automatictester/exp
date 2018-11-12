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
            Queue<Integer> queue = new LinkedList<>();
            queue.add(i);
            elements.add(queue);
        }

        while (elements.size() > 1) {
            Queue<Integer> q1 = elements.poll();
            Queue<Integer> q2 = elements.poll();
            Queue<Integer> merged = merge(q1, q2);
            elements.add(merged);
        }

        Queue<Integer> sortedElements = elements.poll();
        for (int i = 0; i < array.length; i++) {
            array[i] = sortedElements.poll();
        }
    }

    static Queue<Integer> merge(Queue<Integer> q1, Queue<Integer> q2) {
        if (q2 == null) return q1;

        Queue<Integer> merged = new LinkedList<>();

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
