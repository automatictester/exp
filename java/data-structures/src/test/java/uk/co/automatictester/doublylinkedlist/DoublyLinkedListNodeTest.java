package uk.co.automatictester.doublylinkedlist;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DoublyLinkedListNodeTest {

    @Test
    public void testValue() {
        DoublyLinkedListNode<Integer> node = new DoublyLinkedListNode<>(2);
        assertTrue(node.getValue() == 2);
    }

    @Test
    public void testPrev() {
        DoublyLinkedListNode<Integer> node1 = new DoublyLinkedListNode<>(1);
        DoublyLinkedListNode<Integer> node2 = new DoublyLinkedListNode<>(2);
        node2.setPrev(node1);
        assertEquals(node2.getPrev(), node1);
    }

    @Test
    public void testNext() {
        DoublyLinkedListNode<Integer> node1 = new DoublyLinkedListNode<>(1);
        DoublyLinkedListNode<Integer> node2 = new DoublyLinkedListNode<>(2);
        node1.setNext(node2);
        assertEquals(node1.getNext(), node2);
    }
}
