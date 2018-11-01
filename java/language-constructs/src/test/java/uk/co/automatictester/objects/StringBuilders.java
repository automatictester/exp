package uk.co.automatictester.objects;

import org.testng.annotations.Test;

public class StringBuilders {

    @Test
    public void compare() {
        StringBuilder b1 = new StringBuilder();
        b1.append("abc");

        // b1 is mutable! it first applies the reverse() method and then returns already changed value
        StringBuilder b2 = b1.reverse();

        System.out.println(b1 == b2);

        // calls equals on an object
        System.out.println(b1.equals(b2));
        System.out.println(b1.toString() + ", " + b2.toString());
    }
}
