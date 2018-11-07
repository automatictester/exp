package uk.co.automatictester.creational.builder.test;

import org.testng.annotations.Test;
import uk.co.automatictester.creational.builder.MultiFieldClass;

import static org.testng.Assert.*;

public class MultiFieldClassTest {

    @Test
    public void testBuilder() {
        MultiFieldClass multiFieldClass = new MultiFieldClass.Builder(1, 2)
                .withC(4)
                .withD(8)
                .withE(16)
                .build();

        assertEquals(multiFieldClass.toString(), "MultiFieldClass(a: 1, b: 2, c: 4, d: 8, e: 16)");
    }
}
