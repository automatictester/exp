package uk.co.automatictester.functional;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FunctionalComparator {

    class Animal {
        int id;

        Animal(int id) {
            this.id = id;
        }
    }

    @Test
    public void anonymousInnerClass() {
        Animal animal1 = new Animal(10);
        Animal animal2 = new Animal(12);
        Animal animal3 = new Animal(8);
        List<Animal> list = new ArrayList<>();
        list.add(animal1);
        list.add(animal2);
        list.add(animal3);

        list.forEach(a -> System.out.println(a.id));

        Comparator<Animal> byId = new Comparator<Animal>() {
            public int compare(Animal a1, Animal a2) {
                return a1.id - a2.id;
            }
        };

        list.sort(byId);
        list.forEach(a -> System.out.println(a.id));
    }

    @Test
    public void explicit() {
        Animal animal1 = new Animal(10);
        Animal animal2 = new Animal(12);
        Animal animal3 = new Animal(8);
        List<Animal> list = new ArrayList<>();
        list.add(animal1);
        list.add(animal2);
        list.add(animal3);

        list.forEach(a -> System.out.println(a.id));

        Comparator<Animal> byId = (a1, a2) -> a1.id - a2.id;

        list.sort(byId);
        list.forEach(a -> System.out.println(a.id));
    }

    @Test
    public void inline() {
        Animal animal1 = new Animal(10);
        Animal animal2 = new Animal(12);
        Animal animal3 = new Animal(8);
        List<Animal> list = new ArrayList<>();
        list.add(animal1);
        list.add(animal2);
        list.add(animal3);

        list.forEach(a -> System.out.println(a.id));

        list.sort((a1, a2) -> a1.id - a2.id);
        list.forEach(a -> System.out.println(a.id));
    }
}
