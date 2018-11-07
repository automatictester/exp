package uk.co.automatictester.linkedlist;

public class LinkedListNode<T> {

    protected T value;
    private LinkedListNode<T> next;

    public LinkedListNode(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setNext(LinkedListNode<T> node) {
        next = node;
    }

    public LinkedListNode<T> getNext() {
        return next;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof LinkedListNode) {
            if (o == this) {
                return true;
            } else {
                LinkedListNode other = (LinkedListNode) o;
                return value.equals(other.value);
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
