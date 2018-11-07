package uk.co.automatictester.structural.flyweight;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
public class CloudClientFactory {

    private static Map<String, CloudClient> cloudClientStore = new HashMap<>();

    private CloudClientFactory() {
    }

    public static CloudClient getInstance(String region) {
        if (!cloudClientStore.containsKey(region)) {
            CloudClient cloudClient = new CloudClient(region);
            cloudClientStore.put(region, cloudClient);
        }
        return cloudClientStore.get(region);
    }
}
