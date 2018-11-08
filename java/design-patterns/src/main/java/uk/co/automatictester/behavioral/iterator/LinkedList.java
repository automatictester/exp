package uk.co.automatictester.behavioral.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements Iterable<T> {

    private int size = 0;
    private LinkedListNode<T> first;

    public void add(T value) {
        LinkedListNode<T> newNode = newNode(value);
        if (first == null) {
            first = newNode;
        } else {
            LinkedListNode<T> lastNode = getLastNode();
            lastNode.setNext(newNode);
        }
        size++;
    }

    private LinkedListNode<T> newNode(T value) {
        return new LinkedListNode<>(value);
    }

    private LinkedListNode<T> getNode(int index) {
        if (index == 0) {
            return first;
        }
        LinkedListNode<T> currentNode = first;
        for (int i = 1; i <= index; i++) {
            currentNode = currentNode.getNext();
        }
        return currentNode;
    }

    private LinkedListNode<T> getLastNode() {
        return getNode(size - 1);
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<T> {

        private LinkedListNode<T> currentElement = null;

        @Override
        public boolean hasNext() {
            if (size == 0) {
                return false;
            }
            if (currentElement == null) {
                return first != null;
            }
            return currentElement.getNext() != null;
        }

        @Override
        public T next() {
            if (size == 0) {
                throw new NoSuchElementException();
            }
            if (currentElement == null) {
                currentElement = first;
                return first.getValue();
            }
            currentElement = currentElement.getNext();
            if (currentElement == null) {
                throw new NoSuchElementException();
            }
            return currentElement.getValue();
        }
    }
}
