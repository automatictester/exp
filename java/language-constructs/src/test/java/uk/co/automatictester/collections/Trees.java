package uk.co.automatictester.collections;

import org.testng.annotations.Test;

import java.util.TreeSet;

public class Trees {

    @Test
    public void ordering() {
        TreeSet<String> set = new TreeSet<>();
        set.add("Abc");
        set.add("ABC");
        set.add("abc");

        System.out.println(set.ceiling("Ab")); // Abc
        System.out.println(set.floor("Ab")); // ABC
        System.out.println(set.higher("Ab")); // Abc
        System.out.println(set.lower("Ab")); // ABC
    }
}
