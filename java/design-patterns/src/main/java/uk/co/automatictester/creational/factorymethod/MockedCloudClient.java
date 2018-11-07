package uk.co.automatictester.creational.factorymethod;

public class MockedCloudClient implements CloudClient {
    // implementation goes here

    @Override
    public boolean equals(Object o) {
        // simplified check & no hashCode() method
        if (o instanceof MockedCloudClient) {
            return true;
        }
        return false;
    }
}
