package uk.co.automatictester.java9;

import org.testng.annotations.Test;

import java.util.*;

@SuppressWarnings("ALL")
public class CollectionsTest {

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void listJava9() {
        List<Integer> listJava9 = List.of(1, 2, 3);
        listJava9.add(4); // throws UnsupportedOperationException

        List<Integer> listJava8 = Arrays.asList(1, 2, 3);
        listJava8.add(4);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void setJava9() {
        Set<Integer> setJava9 = Set.of(1, 2, 3);
        setJava9.add(4); // throws UnsupportedOperationException

        Set<Integer> setJava8 = new HashSet<>(Arrays.asList(1, 2, 3));
        setJava8.add(4);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void mapJava9() {
        Map<Integer, Integer> mapJava9 = Map.of(1, 11, 2, 22);
        mapJava9.put(3, 33); // throws UnsupportedOperationException

        Map<Integer, Integer> mapJava8 = new HashMap<>() {{
            put(1, 11);
            put(2, 22);
        }};
        mapJava8.put(3, 33);
    }
}
