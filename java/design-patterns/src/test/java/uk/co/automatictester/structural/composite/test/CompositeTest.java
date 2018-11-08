package uk.co.automatictester.structural.composite.test;

import org.testng.annotations.Test;
import uk.co.automatictester.structural.composite.Dir;
import uk.co.automatictester.structural.composite.File;

import static org.testng.Assert.assertEquals;

public class CompositeTest {

    @Test
    public void testComposite() {
        Dir top = new Dir("top");
        Dir mid = new Dir("mid");
        Dir bottom = new Dir("bottom");

        File a = new File("a", 2);
        File b = new File("b", 4);
        File c = new File("c", 8);
        File d = new File("d", 16);
        File e = new File("e", 32);

        top.add(mid);
        top.add(a);

        mid.add(bottom);
        mid.add(b);

        bottom.add(c);
        bottom.add(d);
        bottom.add(e);

        assertEquals(bottom.size(), 32 + 16 + 8);
        assertEquals(mid.size(), bottom.size() + 4);
        assertEquals(top.size(), mid.size() + 2);
    }
}
