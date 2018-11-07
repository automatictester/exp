package uk.co.automatictester.structural.flyweight;

import java.util.HashMap;
import java.util.Map;

public class CloudClientFlyweightFactory {

    private static Map<String, CloudClient> cloudClientStore = new HashMap<>();

    private CloudClientFlyweightFactory() {
    }

    public static CloudClient getInstance(String region) {
        if (!cloudClientStore.containsKey(region)) {
            CloudClient cloudClient = new CloudClient(region);
            cloudClientStore.put(region, cloudClient);
        }
        return cloudClientStore.get(region);
    }
}
