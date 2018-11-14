package uk.co.automatictester.arraylist;

import java.util.Arrays;

public class ArrayList<E> {

    private int size = 0;
    private E[] array = (E[]) new Object[3];

    public boolean add(E element) {
        if (isFull()) {
            expand();
        }
        array[size] = element;
        size++;
        return true;
    }

    private boolean isFull() {
        return size == array.length;
    }

    private void expand() {
        array = Arrays.copyOf(array, 2 * array.length);
    }

    public E get(int index) {
        assertIndex(index);
        return array[index];
    }

    public E remove(int index) {
        assertIndex(index);
        E element = array[index];
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        array[size - 1] = null;
        size--;
        return element;
    }

    private void assertIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
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
