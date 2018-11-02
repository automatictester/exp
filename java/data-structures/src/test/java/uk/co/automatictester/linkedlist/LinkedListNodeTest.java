package uk.co.automatictester.linkedlist;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LinkedListNodeTest {

    @Test
    public void testValue() {
        LinkedListNode<Integer> node = new LinkedListNode<>(2);
        assertTrue(node.getValue() == 2);
    }

    @Test
    public void testNext() {
        LinkedListNode<Integer> node1 = new LinkedListNode<>(1);
        LinkedListNode<Integer> node2 = new LinkedListNode<>(2);
        node1.setNext(node2);
        assertEquals(node1.getNext(), node2);
    }
}
