package uk.co.automatictester.creational.prototype;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CloneableObject {

    /*
     * This class will not use Clonable interface, as no longer recommended by Joshua Bloch
     */

    private List<Integer> list = new ArrayList<>();

    // Use this constructor to create first instance
    public CloneableObject() {
        Random random = new Random();
        list.add(random.nextInt(100));
        list.add(random.nextInt(100));
    }

    // Use this copy constructor to clone the prototype instance
    public CloneableObject(CloneableObject prototype) {
        list = new ArrayList<>(prototype.list);
    }

    public List<Integer> getValues() {
        return new ArrayList<>(list);
    }

    public void updateValues(int a, int b) {
        list.clear();
        list.add(a);
        list.add(b);
    }
}
