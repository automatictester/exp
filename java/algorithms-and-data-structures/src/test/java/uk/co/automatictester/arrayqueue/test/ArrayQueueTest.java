package uk.co.automatictester.arrayqueue.test;

import org.testng.annotations.Test;
import uk.co.automatictester.arrayqueue.ArrayQueue;

import static org.testng.Assert.*;

public class ArrayQueueTest {

    @Test
    public void testOffer() {
        ArrayQueue<Integer> queue = new ArrayQueue<>();
        boolean added = queue.offer(1);
        assertTrue(added);

        queue.offer(1);
        queue.offer(3);
        assertEquals(queue.size(), 3);
    }

    @Test
    public void testExpand() {
        ArrayQueue<Integer> queue = new ArrayQueue<>();
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        assertEquals(queue.size(), 3);
        queue.offer(4);
        assertEquals(queue.size(), 4);
    }

    @Test
    public void testShrink() {
        ArrayQueue<Integer> queue = new ArrayQueue<>();
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
        queue.offer(5);
        queue.offer(6);
        queue.offer(7);
        queue.offer(8);
        queue.offer(9);
        assertEquals((int) queue.poll(), 1);
        assertEquals((int) queue.poll(), 2);
        assertEquals((int) queue.poll(), 3);
        assertEquals((int) queue.poll(), 4);
        assertEquals((int) queue.poll(), 5);
        assertEquals((int) queue.poll(), 6);
        assertEquals((int) queue.poll(), 7);
        queue.offer(10);
        assertEquals((int) queue.poll(), 8);
        assertEquals((int) queue.poll(), 9);
        assertEquals((int) queue.poll(), 10);
        assertNull(queue.poll());
    }

    @Test
    public void testPeek() {
        ArrayQueue<Integer> queue = new ArrayQueue<>();
        assertNull(queue.peek());
        queue.offer(1);
        assertEquals((int) queue.peek(), 1);
        assertEquals((int) queue.peek(), 1);
    }

    @Test
    public void testPoll() {
        ArrayQueue<Integer> queue = new ArrayQueue<>();
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        assertEquals((int) queue.poll(), 1);
        assertEquals((int) queue.poll(), 2);
        assertEquals((int) queue.poll(), 3);
        assertNull(queue.poll());
    }

    @Test
    public void testOfferAndPoll() {
        ArrayQueue<Integer> queue = new ArrayQueue<>();
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        assertEquals((int) queue.poll(), 1);
        queue.offer(4);
        queue.offer(5);
        queue.offer(6);
        assertEquals((int) queue.poll(), 2);
        queue.offer(7);
        queue.offer(8);
        queue.offer(9);
        assertEquals((int) queue.poll(), 3);
    }

    @Test
    public void testPollAll() {
        ArrayQueue<Integer> queue = new ArrayQueue<>();
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
        assertEquals((int) queue.poll(), 1);
        assertEquals((int) queue.poll(), 2);
        assertEquals((int) queue.poll(), 3);
        assertEquals((int) queue.poll(), 4);
        queue.offer(5);
        assertEquals((int) queue.poll(), 5);
        assertNull(queue.poll());
    }

    @Test
    public void testClear() {
        ArrayQueue<Integer> queue = new ArrayQueue<>();
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        assertEquals((int) queue.poll(), 1);
        queue.offer(4);
        queue.offer(5);
        queue.offer(6);
        assertEquals((int) queue.poll(), 2);
        queue.offer(7);
        queue.offer(8);
        queue.offer(9);

        queue.clear();
        assertNull(queue.peek());
    }
}