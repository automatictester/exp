package uk.co.automatictester.collections;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class PreGenerics {

    @Test
    public void plainList() {
        List list = Arrays.asList("a", "b");
//        for (String s : list) { } // DOES NOT COMPILE, THIS IS A LIST OF OBJECTS
        for (Object o: list) { }
    }
}
