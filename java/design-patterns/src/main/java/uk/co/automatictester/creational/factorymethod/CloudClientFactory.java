package uk.co.automatictester.creational.factorymethod;

public class CloudClientFactory {

    private CloudClientFactory() {
    }

    public static CloudClient getInstance(ClientType type) {
        switch (type) {
            case REAL:
                return new RealCloudClient();
            case MOCKED:
                return new MockedCloudClient();
            default:
                throw new IllegalStateException();
        }
    }
}
