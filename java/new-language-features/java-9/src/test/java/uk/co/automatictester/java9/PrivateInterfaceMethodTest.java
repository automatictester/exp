package uk.co.automatictester.java9;

import org.testng.annotations.Test;

public class PrivateInterfaceMethodTest {

    interface MyInterface {

        default void doStuff() {
            doPublicStuff();
            doPrivateStuff();
            doPrivateStaticStuff();
        }

        default void doPublicStuff() {
            System.out.println("Do public stuff");
        }

        private void doPrivateStuff() { // new in Java 9
            System.out.println("Do private stuff");
        }

        private static void doPrivateStaticStuff() { // new in Java 9
            System.out.println("Do static stuff");
        }
    }

    @Test
    public void test() {
        new MyInterface() {
        }.doStuff();
    }
}
