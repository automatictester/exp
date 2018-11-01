package uk.co.automatictester.nested;

import org.testng.annotations.Test;

class Outer {
    private String s = "outer";

    void go() {
        System.out.println(s);
    }

    void goInner() {
        Inner inner = new Inner();
        inner.go();
    }

    class Inner {
        String s = "inner";

        void go() {
            System.out.println(s);
        }

        void goOuter() {
            Outer outer = new Outer();
            outer.go();
        }
    }
}

public class MemberInnerClass {

    @Test
    public void outer() {
        Outer outer = new Outer();
        outer.go();
        outer.goInner();
    }

    @Test
    public void outherInner() {
        Outer.Inner inner = new Outer().new Inner();
        inner.go();
        inner.goOuter();
    }
}
