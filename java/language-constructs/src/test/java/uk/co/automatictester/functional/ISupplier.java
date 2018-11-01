package uk.co.automatictester.functional;

import org.testng.annotations.Test;

import java.util.function.Supplier;

public class ISupplier {

    private static String supplyName(Supplier<String> trait) {
        return trait.get();
    }

    @Test
    public void staticString() {
        String name = supplyName(() -> "joe bloggs");
        System.out.println(name);
    }

    @Test
    public void objectInstance() {
        Supplier<StringBuilder> supplier = () -> new StringBuilder();
        StringBuilder sb = supplier.get();
        sb.append("xxx");
        String s = sb.toString();
        System.out.println(s);
    }
}
