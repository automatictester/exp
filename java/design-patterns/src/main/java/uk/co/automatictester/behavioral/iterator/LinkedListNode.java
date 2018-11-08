package uk.co.automatictester.behavioral.iterator;

class LinkedListNode<T> {

    private T value;
    private LinkedListNode<T> next;

    LinkedListNode(T value) {
        this.value = value;
    }

    T getValue() {
        return value;
    }

    void setNext(LinkedListNode<T> node) {
        next = node;
    }

    LinkedListNode<T> getNext() {
        return next;
    }
}
