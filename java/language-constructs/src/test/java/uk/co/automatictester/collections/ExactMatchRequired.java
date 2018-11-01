package uk.co.automatictester.collections;

import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

public class ExactMatchRequired {

    @Test
    public void typeMatch() {
        Set<Long> set = new HashSet<>();
        set.add(1L);

//        WON'T COMPILE - NONE OF THOSE HAVE EXACT TYPE MATCH
//        set.add(1);
//        set.add(new Integer(1));
//        set.add(1.0);

    }
}
