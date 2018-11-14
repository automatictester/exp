package uk.co.automatictester.sorting;

import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.testng.Assert.assertEquals;

public class IntArrayMergeSorterTest {

    @Test
    public void testMergeQueues() {
        Queue<Integer> q1 = new LinkedList<>();
        q1.add(27);
        q1.add(38);

        Queue<Integer> q2 = new LinkedList<>();
        q2.add(3);
        q2.add(43);

        Queue<Integer> q = IntArrayMergeSorter.merge(q1, q2);
        assertEquals(q.toString(), "[3, 27, 38, 43]");
    }

    @Test
    public void testMergeQueuesOneAlwaysFirst() {
        Queue<Integer> q1 = new LinkedList<>();
        q1.add(2);
        q1.add(3);

        Queue<Integer> q2 = new LinkedList<>();
        q2.add(30);
        q2.add(43);

        Queue<Integer> q = IntArrayMergeSorter.merge(q1, q2);
        assertEquals(q.toString(), "[2, 3, 30, 43]");
    }

    @Test
    public void testMergeFourElementQueues() {
        Queue<Integer> q1 = new LinkedList<>();
        q1.add(1);
        q1.add(3);
        q1.add(5);
        q1.add(6);

        Queue<Integer> q2 = new LinkedList<>();
        q2.add(2);
        q2.add(4);
        q2.add(7);
        q2.add(8);

        Queue<Integer> q = IntArrayMergeSorter.merge(q1, q2);
        assertEquals(q.toString(), "[1, 2, 3, 4, 5, 6, 7, 8]");
    }

    @Test
    public void testMergeQueuesOneNull() {
        Queue<Integer> q1 = new LinkedList<>();
        q1.add(27);
        q1.add(38);

        Queue<Integer> q = IntArrayMergeSorter.merge(q1, null);
        assertEquals(q.toString(), q1.toString());
    }
}
