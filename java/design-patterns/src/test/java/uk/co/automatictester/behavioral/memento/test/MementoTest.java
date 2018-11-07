package uk.co.automatictester.behavioral.memento.test;

import org.testng.annotations.Test;
import uk.co.automatictester.behavioral.memento.ClassWithRestorableState;
import uk.co.automatictester.behavioral.memento.RestorableState;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class MementoTest {

    @Test
    public void testSuccessfulRestore() {
        ClassWithRestorableState instance = new ClassWithRestorableState();
        RestorableState instanceState = instance.captureState();

        instance.updateValues(2, 4);
        assertNotEquals(instance.captureState(), instanceState);

        instance.restoreState(instanceState);
        assertEquals(instance.captureState(), instanceState);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUnsuccessfulRestore() {
        ClassWithRestorableState instanceOne = new ClassWithRestorableState();
        ClassWithRestorableState instanceTwo = new ClassWithRestorableState();

        RestorableState instanceOneState = instanceOne.captureState();
        instanceTwo.restoreState(instanceOneState);
    }

    @Test
    public void testValuesRestoredCorrectly() {
        ClassWithRestorableState instance = new ClassWithRestorableState();
        List<Integer> initialValues = instance.getValues();
        RestorableState capturedState = instance.captureState();

        instance.updateValues(2, 4);
        List<Integer> updatedValues = instance.getValues();
        assertNotEquals(initialValues, updatedValues);

        instance.restoreState(capturedState);
        List<Integer> restoredValues = instance.getValues();
        assertEquals(initialValues, restoredValues);
    }
}
