package uk.co.automatictester.creational.factorymethod;

public class RealCloudClient implements CloudClient {
    // implementation goes here

    @Override
    public boolean equals(Object o) {
        // simplified check & no hashCode() method
        if (o instanceof RealCloudClient) {
            return true;
        }
        return false;
    }
}
