package uk.co.automatictester.java10;

import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("ALL")
public class UnmodifiableCollectionsTest {

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void copyOfList() {
        List<Integer> originalList = List.of(1,2,3);
        List<Integer> unmodifiableCopy = List.copyOf(originalList);
        unmodifiableCopy.add(4); // throws UnsupportedOperationException
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void copyOfSet() {
        Set<Integer> originalSet = Set.of(1,2,3);
        Set<Integer> unmodifiableCopy = Set.copyOf(originalSet);
        unmodifiableCopy.add(4); // throws UnsupportedOperationException
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void copyOfMap() {
        Map<Integer, Integer> originalList = Map.of(1, 11, 2, 22, 3, 33);
        Map<Integer, Integer> unmodifiableCopy = Map.copyOf(originalList);
        unmodifiableCopy.put(4, 44); // throws UnsupportedOperationException
    }
}
