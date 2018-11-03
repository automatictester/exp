package uk.co.automatictester.doublylinkedlist;

import uk.co.automatictester.linkedlist.LinkedListNode;

public class DoublyLinkedListNode<T> extends LinkedListNode<T> {

    private DoublyLinkedListNode<T> prev;
    private DoublyLinkedListNode<T> next;

    public DoublyLinkedListNode(T value) {
        super(value);
    }

    public void setPrev(DoublyLinkedListNode<T> node) {
        prev = node;
    }

    public DoublyLinkedListNode<T> getPrev() {
        return prev;
    }

    public void setNext(DoublyLinkedListNode<T> node) {
        next = node;
    }

    public DoublyLinkedListNode<T> getNext() {
        return next;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof DoublyLinkedListNode) {
            if (o == this) {
                return true;
            } else if (this.value == ((DoublyLinkedListNode) o).value) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
