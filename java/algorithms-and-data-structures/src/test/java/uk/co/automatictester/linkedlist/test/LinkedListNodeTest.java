package uk.co.automatictester.linkedlist.test;

import org.testng.annotations.Test;
import uk.co.automatictester.linkedlist.LinkedListNode;

import static org.testng.Assert.assertEquals;

public class LinkedListNodeTest {

    @Test
    public void testValue() {
        LinkedListNode<Integer> node = new LinkedListNode<>(2);
        assertEquals((int) node.getValue(), 2);
    }

    @Test
    public void testNext() {
        LinkedListNode<Integer> node1 = new LinkedListNode<>(1);
        LinkedListNode<Integer> node2 = new LinkedListNode<>(2);
        node1.setNext(node2);
        assertEquals(node1.getNext(), node2);
    }
}
