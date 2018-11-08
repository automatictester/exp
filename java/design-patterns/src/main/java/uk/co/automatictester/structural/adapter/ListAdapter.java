package uk.co.automatictester.structural.adapter;

public class ListAdapter implements List {

    private WeirdList list = new WeirdList();

    public boolean remove(int i) {
        return list.delete(i);
    }
}
