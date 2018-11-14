package uk.co.automatictester.arraystack;

import java.util.Arrays;

/*
 * Array-backed Stack implementation
 */
public class ArrayStack<E> {

    private int size = 0;
    private E[] array = (E[]) new Object[3];

    public void push(E element) {
        if (isFull()) {
            expand();
        }
        array[size] = element;
        size++;
    }

    private boolean isFull() {
        return size == array.length;
    }

    private void expand() {
        array = Arrays.copyOf(array, 2 * array.length);
    }

    public E peek() {
        if (size == 0) return null;
        return array[size - 1];
    }

    public E pop() {
        if (size == 0) return null;
        E element = array[size - 1];
        array[size - 1] = null;
        size--;
        return element;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    public int size() {
        return size;
    }
}
