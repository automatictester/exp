package uk.co.automatictester.arrayqueue;

import java.util.Arrays;

/*
 * Array-backed Queue implementation
 */
public class ArrayQueue<E> {

    private int size = 0;
    private E[] array = (E[]) new Object[3];
    private int head = 0;
    private int tail = -1;

    public boolean offer(E element) {
        if (isFull()) {
            expand();
        }
        array[tail + 1] = element;
        size++;
        tail++;
        return true;
    }

    private boolean isFull() {
        return tail == array.length - 1;
    }

    private void expand() {
        array = Arrays.copyOf(array, 2 * array.length);
    }

    public E peek() {
        if (size == 0) return null;
        return array[head];
    }

    public E poll() {
        if (size == 0) return null;
        if (shouldShrink()) shrink();
        E element = array[head];
        array[head] = null;
        head++;
        size--;
        return element;
    }

    private boolean shouldShrink() {
        return size >= 3 && array.length / 3 > size;
    }

    private void shrink() {
        shiftLeft();
        array = Arrays.copyOf(array, array.length / 2);
        head = 0;
        tail = size - 1;
    }

    private void shiftLeft() {
        int j = 0;
        for (int i = head; i <= tail; i++) {
            array[j] = array[i];
            array[i] = null;
            j++;
        }
    }

    public void clear() {
        for (int i = head; i <= tail; i++) {
            array[i] = null;
        }
        size = 0;
        head = 0;
        tail = 0;
    }

    public int size() {
        return size;
    }
}
