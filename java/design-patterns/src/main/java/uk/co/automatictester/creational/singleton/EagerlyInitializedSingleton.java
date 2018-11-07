package uk.co.automatictester.creational.singleton;

public class EagerlyInitializedSingleton {

    private static final EagerlyInitializedSingleton INSTANCE = new EagerlyInitializedSingleton();

    private EagerlyInitializedSingleton() {}

    public static EagerlyInitializedSingleton getInstance() {
        return INSTANCE;
    }
}
