package uk.co.automatictester.doublylinkedlist.test;

import org.testng.annotations.Test;
import uk.co.automatictester.doublylinkedlist.DoublyLinkedList;

import static org.testng.Assert.assertEquals;

public class DoublyLinkedListTest {

    @Test
    public void testAdd() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

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
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

        list.addFirst(2);
        assertEquals(list.size(), 1);
        assertEquals((int) list.get(0), 2);
        assertEquals(list.search(2), 0);

        list.addFirst(1);
        assertEquals(list.size(), 2);
        assertEquals((int) list.get(0), 1);
        assertEquals((int) list.get(1), 2);
        assertEquals(list.search(1), 0);
        assertEquals(list.search(2), 1);
    }

    @Test
    public void testAddAt() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

        list.add(0, 1);
        assertEquals(list.size(), 1);
        assertEquals((int) list.get(0), 1);
        assertEquals(list.search(1), 0);

        list.add(1, 3);
        assertEquals(list.size(), 2);
        assertEquals((int) list.get(0), 1);
        assertEquals((int) list.get(1), 3);
        assertEquals(list.search(1), 0);
        assertEquals(list.search(3), 1);

        list.add(1, 2);
        assertEquals(list.size(), 3);
        assertEquals((int) list.get(0), 1);
        assertEquals((int) list.get(1), 2);
        assertEquals((int) list.get(2), 3);
        assertEquals(list.search(1), 0);
        assertEquals(list.search(2), 1);
        assertEquals(list.search(3), 2);
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testAddAtExNegative() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.add(-1, 10);
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testAddAtExTooLarge() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.add(1, 10);
    }

    @Test
    public void testDeleteFromOneElementList() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.add(1);
        assertEquals(list.size(), 1);
        assertEquals((int) list.get(0), 1);
        list.delete(0);
        assertEquals(list.size(), 0);
        assertEquals(list.search(1), -1);
    }

    @Test
    public void testDeleteFirstFromMultiElementList() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.add(1);
        list.add(2);
        list.delete(0);
        assertEquals(list.size(), 1);
        assertEquals((int) list.get(0), 2);
        assertEquals(list.search(1), -1);
    }

    @Test
    public void testDeleteLastFromMultiElementList() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.add(1);
        list.add(2);
        list.delete(1);
        assertEquals(list.size(), 1);
        assertEquals((int) list.get(0), 1);
        assertEquals(list.search(2), -1);
    }

    @Test
    public void testDelete() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        assertEquals(list.size(), 5);

        list.delete(2);
        assertEquals(list.size(), 4);
        assertEquals((int) list.get(0), 1);
        assertEquals((int) list.get(1), 2);
        assertEquals((int) list.get(2), 4);
        assertEquals((int) list.get(3), 5);
        assertEquals(list.search(1), 0);
        assertEquals(list.search(2), 1);
        assertEquals(list.search(3), -1);
        assertEquals(list.search(4), 2);
        assertEquals(list.search(5), 3);
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testDeleteEx() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.delete(0);
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testGetEx() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.get(0);
    }

    @Test
    public void testSearch() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);

        assertEquals(list.search(0), -1);
        assertEquals(list.search(1), 0);
        assertEquals(list.search(2), 1);
        assertEquals(list.search(3), 2);
        assertEquals(list.search(4), 3);
        assertEquals(list.search(5), 4);
        assertEquals(list.search(6), 5);
        assertEquals(list.search(7), -1);
    }

    @Test
    public void testToString() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        assertEquals(list.toString(), "DoublyLinkedList: Empty");

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        assertEquals(list.toString(), "DoublyLinkedList: 1, 2, 3, 4, 5");
    }
}
