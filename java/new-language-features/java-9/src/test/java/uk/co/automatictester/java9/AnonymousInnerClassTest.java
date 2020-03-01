package uk.co.automatictester.java9;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Comparator;

@Slf4j
public class AnonymousInnerClassTest {

    @BeforeClass
    public void setup() {

    }

    @Test
    public void test() {

        // Java 9: diamond operator extension
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
