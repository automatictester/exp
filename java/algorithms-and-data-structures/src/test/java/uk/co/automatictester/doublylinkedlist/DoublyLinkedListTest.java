package uk.co.automatictester.doublylinkedlist;

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

@SuppressWarnings("SimplifiedTestNGAssertion")
public class DoublyLinkedListTest {

    @Test
    public void testAdd() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

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
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

        list.addFirst(2);
        assertTrue(list.size() == 1);
        assertTrue(list.get(0) == 2);
        assertTrue(list.search(2) == 0);

        list.addFirst(1);
        assertTrue(list.size() == 2);
        assertTrue(list.get(0) == 1);
        assertTrue(list.get(1) == 2);
        assertTrue(list.search(1) == 0);
        assertTrue(list.search(2) == 1);
    }

    @Test
    public void testAddAt() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

        list.add(0, 1);
        assertTrue(list.size() == 1);
        assertTrue(list.get(0) == 1);
        assertTrue(list.search(1) == 0);

        list.add(1, 3);
        assertTrue(list.size() == 2);
        assertTrue(list.get(0) == 1);
        assertTrue(list.get(1) == 3);
        assertTrue(list.search(1) == 0);
        assertTrue(list.search(3) == 1);

        list.add(1, 2);
        assertTrue(list.size() == 3);
        assertTrue(list.get(0) == 1);
        assertTrue(list.get(1) == 2);
        assertTrue(list.get(2) == 3);
        assertTrue(list.search(1) == 0);
        assertTrue(list.search(2) == 1);
        assertTrue(list.search(3) == 2);
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
        assertTrue(list.size() == 1);
        assertTrue(list.get(0) == 1);
        list.delete(0);
        assertTrue(list.size() == 0);
        assertTrue(list.search(1) == -1);
    }

    @Test
    public void testDeleteFirstFromMultiElementList() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.add(1);
        list.add(2);
        list.delete(0);
        assertTrue(list.size() == 1);
        assertTrue(list.get(0) == 2);
        assertTrue(list.search(1) == -1);
    }

    @Test
    public void testDeleteLastFromMultiElementList() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.add(1);
        list.add(2);
        list.delete(1);
        assertTrue(list.size() == 1);
        assertTrue(list.get(0) == 1);
        assertTrue(list.search(2) == -1);
    }

    @Test
    public void testDelete() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        assertTrue(list.size() == 5);

        list.delete(2);
        assertTrue(list.size() == 4);
        assertTrue(list.get(0) == 1);
        assertTrue(list.get(1) == 2);
        assertTrue(list.get(2) == 4);
        assertTrue(list.get(3) == 5);
        assertTrue(list.search(1) == 0);
        assertTrue(list.search(2) == 1);
        assertTrue(list.search(3) == -1);
        assertTrue(list.search(4) == 2);
        assertTrue(list.search(5) == 3);
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

        assertTrue(list.search(0) == -1);
        assertTrue(list.search(1) == 0);
        assertTrue(list.search(2) == 1);
        assertTrue(list.search(3) == 2);
        assertTrue(list.search(4) == 3);
        assertTrue(list.search(5) == 4);
        assertTrue(list.search(6) == 5);
        assertTrue(list.search(7) == -1);
    }

    @Test
    public void testToString() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        assertTrue(list.toString().equals("DoublyLinkedList: Empty"));

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        assertTrue(list.toString().equals("DoublyLinkedList: 1, 2, 3, 4, 5"));
    }
}
