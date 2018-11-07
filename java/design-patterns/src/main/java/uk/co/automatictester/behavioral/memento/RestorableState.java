package uk.co.automatictester.behavioral.memento;

public class RestorableState {
    int a;
    int b;
    private int originatorInstanceId;

    RestorableState(int originatorInstanceId) {
        this.originatorInstanceId = originatorInstanceId;
    }

    int getOriginatorInstanceId() {
        return originatorInstanceId;
    }

    RestorableState copyOf() {
        RestorableState copy = new RestorableState(originatorInstanceId);
        copy.a = a;
        copy.b = b;
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof RestorableState) {
            RestorableState other = (RestorableState) o;
            return a == other.a
                    && b == other.b
                    && originatorInstanceId == other.originatorInstanceId;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return a + b + originatorInstanceId;
    }
}
