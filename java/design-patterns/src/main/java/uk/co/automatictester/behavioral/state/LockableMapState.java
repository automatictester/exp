package uk.co.automatictester.behavioral.state;

public interface LockableMapState<K, V> {
    void put(K key, V value);
    V get(K key);
}
