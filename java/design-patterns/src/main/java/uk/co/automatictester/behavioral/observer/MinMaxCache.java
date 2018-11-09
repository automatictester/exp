package uk.co.automatictester.behavioral.observer;

import java.util.Comparator;
import java.util.List;

public class MinMaxCache implements Observer {

    private int min;
    private int max;

    @Override
    public void update(List<Integer> list) {
        min = list.stream().min(Comparator.naturalOrder()).get();
        max = list.stream().max(Comparator.naturalOrder()).get();
        System.out.println("Updated min value to: " + min);
        System.out.println("Updated max value to: " + max);
    }
}
