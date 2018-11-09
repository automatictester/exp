package uk.co.automatictester.behavioral.observer;

import java.util.*;

public class Data implements Subject {
    private List<Integer> list = new ArrayList<>();
    private Set<Observer> observers = new HashSet<>();

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
        observer.update(list);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(o -> o.update(list));
    }

    public void changeState() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            list.add(random.nextInt(100));
        }
        notifyObservers();
    }
}
