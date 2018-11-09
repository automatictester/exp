package uk.co.automatictester.behavioral.observer;

import java.util.List;

public class SumCache implements Observer {

    private int sum;

    @Override
    public void update(List<Integer> list) {
        sum = list.stream().mapToInt(e -> e).sum();
        System.out.println("Updated sum to: " + sum);
    }
}
