package uk.co.automatictester.java9;

import lombok.AllArgsConstructor;
import org.testng.annotations.Test;

import java.util.Comparator;

@SuppressWarnings("ALL")
public class AnonymousInnerClassTest {

    @Test
    public void test() {

        // no longer needs: new Comparator<Animal>()
        Comparator<Animal> byId = new Comparator<>() {
            public int compare(Animal a1, Animal a2) {
                return a1.id - a2.id;
            }
        };
    }

    @AllArgsConstructor
    class Animal {
        int id;
    }
}
