package uk.co.automatictester.nested;

import org.testng.annotations.Test;

public class LocalInnerClass {

    @Test
    public void localInner() {
        OuterB outer = new OuterB();
        outer.goLocal();
    }
}

class OuterB {
    String s = "outer";

    void go() {
        System.out.println(s);
    }

    void goLocal() {
        String local = "local variable";

        class Inner {
            String s = "inner";

            void go() {
                System.out.println(s);
            }

            void goLocal() {
                System.out.println(local); // can access local variables if final or effectively final
            }

            void goOuterVariable() {
                System.out.println(OuterB.this.s); // can access instance variables of outer class
            }

            void goOuterMethod() {
                OuterB.this.go(); // can call instance methods of outer class
            }
        }

        Inner inner = new Inner();
        inner.go();
        inner.goLocal();
        inner.goOuterVariable();
        inner.goOuterMethod();
    }
}
