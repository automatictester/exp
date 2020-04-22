package uk.co.automatictester.creational.singleton;

public class LazilyInitializedSingletonHolder {

    private static class SingletonHolder {
        public static MyResource myResource = new MyResource();
    }

    public static MyResource getMyResource() {
        return SingletonHolder.myResource;
    }

    private LazilyInitializedSingletonHolder() {
    }
}

class MyResource {
}
