package uk.co.automatictester.structural.adapter;

public class Client {

    public static void main(String[] args) {
        List list = new ListAdapter();
        list.remove(1);
    }
}
