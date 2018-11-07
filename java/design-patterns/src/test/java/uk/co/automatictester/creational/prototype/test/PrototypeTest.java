package uk.co.automatictester.creational.prototype.test;

import org.testng.annotations.Test;
import uk.co.automatictester.creational.prototype.CloneableObject;

import java.util.List;

import static org.testng.Assert.*;

public class PrototypeTest {

    @Test
    public void testCopyConstructor() {
        CloneableObject prototypeInstance = new CloneableObject();
        CloneableObject anotherInstance = new CloneableObject(prototypeInstance);
        assertNotSame(prototypeInstance, anotherInstance);

        List<Integer> prototypeInstanceValues = prototypeInstance.getValues();
        List<Integer> anotherInstanceValues = anotherInstance.getValues();
        assertEquals(prototypeInstanceValues, anotherInstanceValues);

        prototypeInstance.updateValues(1, 2);
        assertNotEquals(prototypeInstance.getValues(), anotherInstanceValues);
    }
}
