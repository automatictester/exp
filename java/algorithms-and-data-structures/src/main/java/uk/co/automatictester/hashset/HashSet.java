package uk.co.automatictester.hashset;

import java.util.ArrayList;
import java.util.List;

public class HashSet<E> {

    private int size = 0;
    private ArrayList<E>[] array = new ArrayList[3];
    private static final int LOAD_FACTOR = 1;

    public HashSet() {
        for (int i = 0; i < array.length; i++) {
            array[i] = new ArrayList<>();
        }
    }

    public boolean add(E element) {
        if (size == array.length * LOAD_FACTOR) rehash();
        int targetBucket = bucket(element);
        ArrayList<E> list = array[targetBucket];
        if (list.contains(element)) return false;
        list.add(element);
        size++;
        return true;
    }

    private void rehash() {
        ArrayList<E> elements = new ArrayList<>();
        for (List<E> list : array) {
            elements.addAll(list);
        }
        array = new ArrayList[2 * size];
        for (int i = 0; i < array.length; i++) {
            array[i] = new ArrayList<>();
        }
        for (E e : elements) {
            int targetBucket = bucket(e);
            ArrayList<E> list = array[targetBucket];
            if (list.contains(e)) return;
            list.add(e);
        }
    }

    private int bucket(E element) {
        return element.hashCode() % array.length;
    }

    public boolean contains(E element) {
        int targetBucket = bucket(element);
        ArrayList<E> list = array[targetBucket];
        return list.contains(element);
    }

    public boolean remove(E element) {
        int targetBucket = bucket(element);
        ArrayList<E> list = array[targetBucket];
        boolean found = list.remove(element);
        if (found) size--;
        return found;
    }

    public int size() {
        return size;
    }

    public void clear() {
        for (int i = 0; i < array.length; i++) {
            array[i] = new ArrayList<>();
        }
        size = 0;
    }
}
