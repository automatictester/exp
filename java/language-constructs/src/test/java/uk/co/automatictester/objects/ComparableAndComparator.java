package uk.co.automatictester.objects;

import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class ComparableAndComparator {

    /*
     * - technically possible to implement both - practical purpose unknown
     * - will use Comparable.compareTo() for sorting, unless Comparator.compare() is explicitly specified
     */

    class ComparableClass implements Comparable<ComparableClass>, Comparator<ComparableClass> {

        private int id;

        public ComparableClass(int id) {
            this.id = id;
        }

        @Override
        public int compareTo(ComparableClass other) {
            return this.id - other.id;
        }

        @Override
        public int compare(ComparableClass c1, ComparableClass c2) {
            return c1.id - c2.id; // won't be used in our example
        }
    }

    @Test
    public void sortUsingCompareTo() {
        ComparableClass c1 = new ComparableClass(1);
        ComparableClass c2 = new ComparableClass(2);
        ComparableClass c3 = new ComparableClass(3);

        Set<ComparableClass> set = new TreeSet<>();
        set.add(c3);
        set.add(c1);
        set.add(c2);

        set.forEach(x -> System.out.println(x.id));
    }

    @Test
    public void sortUsingComparatorPassedInTreeSetConstructor() {
        ComparableClass c1 = new ComparableClass(1);
        ComparableClass c2 = new ComparableClass(2);
        ComparableClass c3 = new ComparableClass(3);

        Comparator<ComparableClass> comparator = (o1, o2) -> o2.id - o1.id; // reverse sort

        Set<ComparableClass> set = new TreeSet<>(comparator);
        set.add(c3);
        set.add(c1);
        set.add(c2);

        set.forEach(x -> System.out.println(x.id));
    }
}
