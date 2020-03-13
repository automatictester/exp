package uk.co.automatictester.java10;

import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UnmodifiableCollectorsTest {

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void toUnmodifiableList() {
        List<Integer> unmodifiableList = Stream.of(1, 2, 3).collect(Collectors.toUnmodifiableList());
        unmodifiableList.add(4); // throws UnsupportedOperationException
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void toUnmodifiableSet() {
        Set<Integer> unmodifiableList = Stream.of(1, 2, 3).collect(Collectors.toUnmodifiableSet());
        unmodifiableList.add(4); // throws UnsupportedOperationException
    }
}
