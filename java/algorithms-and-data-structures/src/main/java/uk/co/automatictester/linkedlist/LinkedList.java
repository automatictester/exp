package uk.co.automatictester.linkedlist;

public class LinkedList<T> {

    protected int size = 0;
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

    public void addFirst(T value) {
        LinkedListNode<T> newNode = newNode(value);
        if (first == null) {
            first = newNode;
        } else {
            newNode.setNext(first);
            first = newNode;
        }
        size++;
    }

    public void add(int index, T value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0 && size == 0) {
            first = newNode(value);
        } else if (index == size) {
            LinkedListNode<T> lastNode = getLastNode();
            lastNode.setNext(newNode(value));
        } else {
            LinkedListNode<T> nodeBefore = getNode(index - 1);
            LinkedListNode<T> nodeAtIndex = nodeBefore.getNext();
            LinkedListNode<T> newNode = newNode(value);

            newNode.setNext(nodeAtIndex);
            nodeBefore.setNext(newNode);
        }
        size++;
    }

    public void delete(int index) {
        assertIndex(index);
        if (index == 0 && size > 0) {
            LinkedListNode<T> nodeToDelete = getNode(index);
            first = getNode(index + 1);
            nodeToDelete.setNext(null);
        } else if (index == size - 1) {
            LinkedListNode<T> nodeLastMinusOne = getNode(index - 1);
            nodeLastMinusOne.setNext(null);
        } else {
            LinkedListNode<T> nodeBeforeDelete = getNode(index - 1);
            LinkedListNode<T> nodeToDelete = nodeBeforeDelete.getNext();
            LinkedListNode<T> nodeAfterDelete = nodeToDelete.getNext();

            nodeBeforeDelete.setNext(nodeAfterDelete);
            nodeToDelete.setNext(null);
        }
        size--;
    }

    public T get(int index) {
        return getNode(index).getValue();
    }

    public int search(T value) {
        for (int i = 0; i < size; i++) {
            if (get(i).equals(value)) {
                return i;
            }
        }
        return -1;
    }

    public int size() {
        return size;
    }

    public String toString() {
        String text = "LinkedList: ";
        if (size == 0) {
            text += "Empty";
            return text;
        }
        LinkedListNode<T> node = first;
        while (node != null) {
            if (node.getNext() != null) {
                text += String.format("%s, ", node.value);
            } else {
                text += node.value;
            }
            node = node.getNext();
        }
        return text;
    }

    protected void assertIndex(int index) {
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private LinkedListNode<T> newNode(T value) {
        return new LinkedListNode<>(value);
    }

    private LinkedListNode<T> getNode(int index) {
        assertIndex(index);
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
}
