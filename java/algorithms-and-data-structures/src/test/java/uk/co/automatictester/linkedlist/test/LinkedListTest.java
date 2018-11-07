package uk.co.automatictester.linkedlist.test;

import org.testng.annotations.Test;
import uk.co.automatictester.linkedlist.LinkedList;

import static org.testng.Assert.assertEquals;

public class LinkedListTest {

    @Test
    public void testAdd() {
        LinkedList<Integer> list = new LinkedList<>();

        list.add(1);
        list.add(2);
        list.add(3);

        assertEquals(list.size(), 3);
        assertEquals((int) list.get(0), 1);
        assertEquals((int) list.get(1), 2);
        assertEquals((int) list.get(2), 3);
    }

    @Test
    public void testAddFirst() {
        LinkedList<Integer> list = new LinkedList<>();

        list.addFirst(2);
        assertEquals(list.size(), 1);
        assertEquals((int) list.get(0), 2);

        list.addFirst(1);
        assertEquals(list.size(), 2);
        assertEquals((int) list.get(0), 1);
        assertEquals((int) list.get(1), 2);
    }

    @Test
    public void testAddAt() {
        LinkedList<Integer> list = new LinkedList<>();

        list.add(0, 1);
        assertEquals(list.size(), 1);
        assertEquals((int) list.get(0), 1);

        list.add(1, 2);
        assertEquals(list.size(), 2);
        assertEquals((int) list.get(0), 1);
        assertEquals((int) list.get(1), 2);

        list.add(1, 30);
        assertEquals(list.size(), 3);
        assertEquals((int) list.get(0), 1);
        assertEquals((int) list.get(1), 30);
        assertEquals((int) list.get(2), 2);
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testAddAtExNegative() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(-1, 10);
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testAddAtExTooLarge() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1, 10);
    }

    @Test
    public void testDelete() {
        LinkedList<Integer> list = new LinkedList<>();

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        assertEquals(list.size(), 5);

        list.delete(0);
        assertEquals(list.size(), 4);
        assertEquals((int) list.get(0), 2);
        assertEquals((int) list.get(1), 3);
        assertEquals((int) list.get(2), 4);
        assertEquals((int) list.get(3), 5);

        list.delete(3);
        assertEquals(list.size(), 3);
        assertEquals((int) list.get(0), 2);
        assertEquals((int) list.get(1), 3);
        assertEquals((int) list.get(2), 4);

        list.delete(1);
        assertEquals(list.size(), 2);
        assertEquals((int) list.get(0), 2);
        assertEquals((int) list.get(1), 4);
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testDeleteEx() {
        LinkedList<Integer> list = new LinkedList<>();
        list.delete(0);
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testGetEx() {
        LinkedList<Integer> list = new LinkedList<>();
        list.get(0);
    }

    @Test
    public void testSearch() {
        LinkedList<Integer> list = new LinkedList<>();

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        assertEquals(list.search(0), -1);
        assertEquals(list.search(1), 0);
        assertEquals(list.search(5), 4);
        assertEquals(list.search(6), -1);
    }

    @Test
    public void testToString() {
        LinkedList<Integer> list = new LinkedList<>();
        assertEquals(list.toString(), "LinkedList: Empty");

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        assertEquals(list.toString(), ("LinkedList: 1, 2, 3, 4, 5"));
    }
}
