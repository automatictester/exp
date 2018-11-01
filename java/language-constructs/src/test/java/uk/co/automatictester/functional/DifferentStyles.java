package uk.co.automatictester.functional;

import org.testng.annotations.Test;

import java.util.function.BiPredicate;

public class DifferentStyles {

    private static boolean isRight(boolean b1, boolean b2, BiPredicate<Boolean, Boolean> p) {
        return p.test(b1, b2);
    }

    @Test
    public void separateMethodAndInlineLambda() {
        boolean b = isRight(true, true, (b1, b2) -> b1 && b2);
        System.out.println(b);
    }

    @Test
    public void separateMethodAndExplicitLambda() {
        BiPredicate<Boolean, Boolean> p = (b1, b2) -> b1 && b2;
        boolean b = isRight(true, true, p);
        System.out.println(b);
    }

    @Test
    public void noSeparateMethod() {
        BiPredicate<Boolean, Boolean> p = (b1, b2) -> b1 && b2;
        boolean b = p.test(true, true);
        System.out.println(b);
    }

    @Test
    public void unusualWithAnonymousInnerClass() {
        BiPredicate<Boolean, Boolean> p = new BiPredicate<Boolean, Boolean>() {
            public boolean test(Boolean b1, Boolean b2) {
                return b1 && b2;
            }
        };
        boolean b = isRight(true, true, p);
        System.out.println(b);
    }
}
