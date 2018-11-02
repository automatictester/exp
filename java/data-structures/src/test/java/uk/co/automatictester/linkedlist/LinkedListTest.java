package uk.co.automatictester.linkedlist;

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

@SuppressWarnings("SimplifiedTestNGAssertion")
public class LinkedListTest {

    @Test
    public void testAdd() {
        LinkedList<Integer> list = new LinkedList<>();

        list.add(1);
        list.add(2);
        list.add(3);

        assertTrue(list.size() == 3);
        assertTrue(list.get(0) == 1);
        assertTrue(list.get(1) == 2);
        assertTrue(list.get(2) == 3);
    }

    @Test
    public void testAddFirst() {
        LinkedList<Integer> list = new LinkedList<>();

        list.addFirst(2);
        assertTrue(list.size() == 1);
        assertTrue(list.get(0) == 2);

        list.addFirst(1);
        assertTrue(list.size() == 2);
        assertTrue(list.get(0) == 1);
        assertTrue(list.get(1) == 2);
    }

    @Test
    public void testMidAdd() {
        LinkedList<Integer> list = new LinkedList<>();

        list.add(0, 1);
        assertTrue(list.size() == 1);
        assertTrue(list.get(0) == 1);

        list.add(1, 2);
        assertTrue(list.size() == 2);
        assertTrue(list.get(0) == 1);
        assertTrue(list.get(1) == 2);

        list.add(1, 30);
        assertTrue(list.size() == 3);
        assertTrue(list.get(0) == 1);
        assertTrue(list.get(1) == 30);
        assertTrue(list.get(2) == 2);
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testMidAddExNegative() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(-1, 10);
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testMidAddExTooLarge() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1, 10);
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testGet() {
        LinkedList<Integer> list = new LinkedList<>();
        list.get(0);
    }

    @Test
    public void testDelete() {
        LinkedList<Integer> list = new LinkedList<>();

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        assertTrue(list.size() == 5);

        list.delete(0);
        assertTrue(list.size() == 4);
        assertTrue(list.get(0) == 2);
        assertTrue(list.get(1) == 3);
        assertTrue(list.get(2) == 4);
        assertTrue(list.get(3) == 5);

        list.delete(3);
        assertTrue(list.size() == 3);
        assertTrue(list.get(0) == 2);
        assertTrue(list.get(1) == 3);
        assertTrue(list.get(2) == 4);

        list.delete(1);
        assertTrue(list.size() == 2);
        assertTrue(list.get(0) == 2);
        assertTrue(list.get(1) == 4);
    }

    @Test
    public void testSearch() {
        LinkedList<Integer> list = new LinkedList<>();

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        assertTrue(list.search(0) == -1);
        assertTrue(list.search(5) == 4);
    }

    @Test
    public void testToString() {
        LinkedList<Integer> list = new LinkedList<>();
        assertTrue(list.toString().equals("LinkedList: Empty"));

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        assertTrue(list.toString().equals("LinkedList: 1, 2, 3, 4, 5"));
    }
}
