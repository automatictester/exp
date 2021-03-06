package uk.co.automatictester.creational.singleton;

public class StaticSingleton {

    private static final StaticSingleton INSTANCE;

    static {
        INSTANCE = new StaticSingleton();
    }

    private StaticSingleton() {}

    public static StaticSingleton getInstance() {
        return INSTANCE;
    }
}
