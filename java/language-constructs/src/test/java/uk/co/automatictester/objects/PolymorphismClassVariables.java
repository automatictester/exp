package uk.co.automatictester.objects;

import org.testng.annotations.Test;

public class PolymorphismClassVariables {

    static class Parent {
        String value = "parent";
    }

    static class Child extends Parent {
        String value = "child";
    }

    @Test
    public void test() {
        Parent f = new Child();
        Child b = new Child();
        System.out.println(f.value);
        System.out.println(b.value);
    }
}
