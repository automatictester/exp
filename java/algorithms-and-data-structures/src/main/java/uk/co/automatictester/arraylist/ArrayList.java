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
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        return array[index];
    }

    public E remove(int index) {
        E element = array[index];
        for (int i = index; i < size; i++) {
            array[i] = array[i + 1];
        }
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
