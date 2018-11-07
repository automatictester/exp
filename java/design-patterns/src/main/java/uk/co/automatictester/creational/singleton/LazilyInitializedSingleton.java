package uk.co.automatictester.creational.singleton;

public class LazilyInitializedSingleton {

    private static LazilyInitializedSingleton instance;

    private LazilyInitializedSingleton() {
    }

    public static synchronized LazilyInitializedSingleton getInstance() {
        if (instance == null) {
            instance = new LazilyInitializedSingleton();
        }
        return instance;
    }
}
