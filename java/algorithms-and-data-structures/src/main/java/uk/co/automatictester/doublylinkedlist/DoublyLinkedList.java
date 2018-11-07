package uk.co.automatictester.doublylinkedlist;

import uk.co.automatictester.linkedlist.LinkedList;

public class DoublyLinkedList<T> extends LinkedList<T> {

    private DoublyLinkedListNode<T> first;
    private DoublyLinkedListNode<T> last;

    @Override
    public void add(T value) {
        DoublyLinkedListNode<T> newNode = newNode(value);
        if (first == null) {
            first = newNode;
            last = newNode;
        } else {
            last.setNext(newNode);
            newNode.setPrev(last);
            last = newNode;
        }
        size++;
    }

    @Override
    public void addFirst(T value) {
        DoublyLinkedListNode<T> newNode = newNode(value);
        if (first == null) {
            first = newNode;
            last = newNode;
        } else {
            newNode.setNext(first);
            first.setPrev(newNode);
            first = newNode;
        }
        size++;
    }

    @Override
    public void add(int index, T value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        DoublyLinkedListNode<T> newNode = newNode(value);
        if (index == 0 && size == 0) {
            first = newNode;
            last = newNode;
        } else if (index == size) {
            last.setNext(newNode);
            newNode.setPrev(last);
            last = newNode;
        } else {
            DoublyLinkedListNode<T> nodeAtIndex = getNode(index);
            DoublyLinkedListNode<T> nodeBefore = getNode(index - 1);

            newNode.setNext(nodeAtIndex);
            newNode.setPrev(nodeBefore);
            nodeAtIndex.setPrev(newNode);
            nodeBefore.setNext(newNode);
        }
        size++;
    }

    @Override
    public void delete(int index) {
        assertIndex(index);
        if (index == 0 && size == 1) {
            first = null;
            last = null;
        } else if (index == 0 && size > 1) {
            DoublyLinkedListNode<T> nodeToDelete = getNode(index);
            DoublyLinkedListNode<T> nodeAfter = getNode(index + 1);

            nodeToDelete.setNext(null);
            nodeAfter.setPrev(null);
            first = nodeAfter;
        } else if (index == size - 1) {
            DoublyLinkedListNode<T> nodeBefore = getNode(index - 1);
            DoublyLinkedListNode<T> nodeToDelete = getNode(index);

            nodeToDelete.setPrev(null);
            nodeBefore.setNext(null);
            last = nodeBefore;
        } else {
            DoublyLinkedListNode<T> nodeBefore = getNode(index - 1);
            DoublyLinkedListNode<T> nodeToDelete = getNode(index);
            DoublyLinkedListNode<T> nodeAfter = getNode(index + 1);

            nodeBefore.setNext(nodeAfter);
            nodeAfter.setPrev(nodeBefore);
            nodeToDelete.setPrev(null);
            nodeToDelete.setNext(null);
        }
        size--;
    }

    @Override
    public T get(int index) {
        return getNode(index).getValue();
    }

    @Override
    public int search(T value) {
        for (int i = 0; i < size; i++) {
            if (get(i).equals(value)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        String text = "DoublyLinkedList: ";
        if (size == 0) {
            text += "Empty";
        }
        for (int i = 0; i < size; i++) {
            if (i < size - 1) {
                text += String.format("%s, ", get(i));
            } else {
                text += get(i);
            }
        }
        return text;
    }

    private DoublyLinkedListNode<T> newNode(T value) {
        return new DoublyLinkedListNode<>(value);
    }

    private DoublyLinkedListNode<T> getNode(int index) {
        assertIndex(index);
        if (index == 0) {
            return first;
        }
        if (index == size - 1) {
            return last;
        }

        DoublyLinkedListNode<T> currentNode;
        if (index < size / 2) {
            currentNode = first;
            for (int i = 1; i <= index; i++) {
                currentNode = currentNode.getNext();
            }
        } else {
            currentNode = last;
            for (int i = 1; i < size - index; i++) {
                currentNode = currentNode.getPrev();
            }
        }

        return currentNode;
    }
}
