package uk.co.automatictester.singleton;

public class StaticSingleton {

    private static final StaticSingleton INSTANCE;

    static {
        INSTANCE = new StaticSingleton();
    }

    private StaticSingleton() {}

    public StaticSingleton getInstance() {
        return INSTANCE;
    }
}
