package uk.co.automatictester.objects;

import org.testng.annotations.Test;

import java.util.ArrayList;

public class Instanceof {

    interface I {
    }

    class A implements I {
    }

    class B extends A {
    }

    class C1 extends B {
    }

    class C2 extends B {
    }

    @Test
    public void instanceofInheritance() {
        A a = new A();
        B b = new B();
        C1 c1 = new C1();
        C2 c2 = new C2();
//        boolean b = c1 instanceof C2; // COMPILATION ERROR
    }

    @Test
    public void instanceofNull() {
        boolean b = null instanceof String;
    }

    @Test
    public void instanceofInterface() {
        boolean b1 = new ArrayList<String>() instanceof I;
        boolean b2 = new ArrayList<String>() instanceof Runnable;
//        boolean b3 = new ArrayList<String>() instanceof A; // COMPILATION ERROR
    }
}
