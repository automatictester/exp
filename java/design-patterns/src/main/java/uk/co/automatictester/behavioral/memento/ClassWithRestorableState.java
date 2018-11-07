package uk.co.automatictester.behavioral.memento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ClassWithRestorableState {

    private int instanceId = System.identityHashCode(this);
    private RestorableState state = new RestorableState(instanceId);

    public ClassWithRestorableState() {
        Random random = new Random();
        state.a = random.nextInt(100);
        state.b = random.nextInt(100);
    }

    public RestorableState captureState() {
        return state.copyOf();
    }

    public void restoreState(RestorableState state) {
        if (state.getOriginatorInstanceId() == instanceId) {
            this.state = state;
        } else {
            throw new IllegalArgumentException("State from/to instances differ");
        }
    }

    public void updateValues(int a, int b) {
        state.a = a;
        state.b = b;
    }

    public List<Integer> getValues() {
        return new ArrayList<>(Arrays.asList(state.a, state.b));
    }

    @Override
    public String toString() {
        return String.format("ClassWithRestorableState(a: %d, b: %d)", state.a, state.b);
    }
}
